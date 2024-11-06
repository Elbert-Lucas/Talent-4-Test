-- Triggers para auditoria de "customer" e "address" após CREATE

CREATE TRIGGER aud_address_create
AFTER INSERT ON TB_ADDRESS
FOR EACH ROW
BEGIN
    INSERT INTO TB_ADDRESS_AUD (address_id, change_type, state, city, street, created_at)
    VALUES (NEW.id, 1, NEW.state, NEW.city, NEW.street, CURRENT_TIMESTAMP);
    SET @last_address_aud_id = LAST_INSERT_ID();
END;

CREATE TRIGGER aud_customer_create
AFTER INSERT ON TB_CUSTOMER
FOR EACH ROW
BEGIN
    INSERT INTO TB_CUSTOMER_AUD (customer_id, change_type, name, email, address_id, created_at)
    VALUES (NEW.id, 1, NEW.name, NEW.email, @last_address_aud_id, CURRENT_TIMESTAMP);
END;

-- Triggers para auditoria de "customer" e "address" após UPDATE

CREATE TRIGGER aud_address_update
AFTER UPDATE ON TB_ADDRESS
FOR EACH ROW
BEGIN
    INSERT INTO TB_ADDRESS_AUD (address_id, change_type, state, city, street, created_at)
    VALUES (NEW.id, 3, NEW.state, NEW.city, NEW.street, CURRENT_TIMESTAMP);
    SET @last_address_aud_id = LAST_INSERT_ID();
END;

CREATE TRIGGER aud_customer_update
AFTER UPDATE ON TB_CUSTOMER
FOR EACH ROW
BEGIN
    INSERT INTO TB_CUSTOMER_AUD (customer_id, change_type, name, email, address_id, created_at)
    VALUES (NEW.id, 3, NEW.name, NEW.email, @last_address_aud_id, CURRENT_TIMESTAMP);
END;

-- Triggers para auditoria de "customer" e "address" após DELETE

CREATE TRIGGER aud_address_delete
AFTER DELETE ON TB_ADDRESS
FOR EACH ROW
BEGIN
    INSERT INTO TB_ADDRESS_AUD (address_id, change_type, state, city, street, created_at)
    VALUES (OLD.id, 4, OLD.state, OLD.city, OLD.street, CURRENT_TIMESTAMP);
    SET @last_address_aud_id = LAST_INSERT_ID();
END;

CREATE TRIGGER aud_customer_delete
AFTER DELETE ON TB_CUSTOMER
FOR EACH ROW
BEGIN
    INSERT INTO TB_CUSTOMER_AUD (customer_id, change_type, name, email, address_id, created_at)
    VALUES (OLD.id, 4, OLD.name, OLD.email, @last_address_aud_id, CURRENT_TIMESTAMP);
END;
