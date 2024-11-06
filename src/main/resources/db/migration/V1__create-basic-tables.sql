SET time_zone = '-03:00';

CREATE TABLE TB_AUDITION (
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(10) NOT NULL UNIQUE,
    PRIMARY KEY (id)
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
    address_id bigint(20) unsigned NOT NULL,
    change_type INT NOT NULL,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_address_aud_change_type_fk FOREIGN KEY (change_type) REFERENCES TB_AUDITION(id)
);

CREATE TABLE TB_CUSTOMER (
    id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    name VARCHAR(75) NOT NULL,
    email VARCHAR(75) NOT NULL UNIQUE,
    address_id  bigint(20) unsigned NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT tb_customer_address_fk FOREIGN KEY (address_id) REFERENCES TB_ADDRESS(id) ON DELETE CASCADE
);
CREATE TABLE TB_CUSTOMER_AUD (
    id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    customer_id bigint(20) unsigned NOT NULL,
    change_type INT NOT NULL,
    name VARCHAR(75) NOT NULL,
    email VARCHAR(75) NOT NULL,
    address_id bigint(20) unsigned,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_customer_aud_change_type_fk FOREIGN KEY (change_type) REFERENCES TB_AUDITION(id),
    CONSTRAINT fk_customer_aud_address_fk FOREIGN KEY (address_id) REFERENCES TB_ADDRESS_AUD(id)
);
