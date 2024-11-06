-- Trigger para deletar "address" em cascada após deleção de "customer"

CREATE TRIGGER delete_address_on_customer_delete
BEFORE DELETE ON TB_CUSTOMER
FOR EACH ROW
BEGIN
    DELETE FROM TB_ADDRESS WHERE id = OLD.address_id;
END;
