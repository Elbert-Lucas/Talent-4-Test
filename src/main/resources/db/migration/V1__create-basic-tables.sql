CREATE TABLE REVINFO (
    rev INT NOT NULL AUTO_INCREMENT,
    revtimestamp TIMESTAMP,
    PRIMARY KEY (rev)
);

CREATE TABLE TB_ADDRESS (
    id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE TB_ADDRESS_AUD (
    id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    rev INT NOT NULL,
    revtype TINYINT,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_address_aud_rev FOREIGN KEY (rev) REFERENCES REVINFO(rev),
    CONSTRAINT fk_address_aud FOREIGN KEY (id) REFERENCES TB_ADDRESS(id)
);

CREATE TABLE TB_CUSTOMERS (
    id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    name VARCHAR(75) NOT NULL,
    email VARCHAR(75) NOT NULL,
    address_id bigint(20) unsigned NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT tb_customers_address_fk FOREIGN KEY (address_id) REFERENCES TB_ADDRESS(id)
);
CREATE TABLE TB_CUSTOMERS_AUD (
    id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    rev INT NOT NULL,
    revtype TINYINT,
    name VARCHAR(75) NOT NULL,
    email VARCHAR(75) NOT NULL,
    address_id bigint(20) unsigned,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_customers_aud_rev FOREIGN KEY (rev) REFERENCES REVINFO(rev),
    CONSTRAINT fk_customers_aud FOREIGN KEY (id) REFERENCES TB_CUSTOMERS(id)
);
