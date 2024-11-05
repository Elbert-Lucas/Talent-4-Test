package br.com.talent4.customer.enums;

import lombok.Getter;

@Getter
public enum CRUD {
    CREATE (1),
    READ (2),
    UPDATE (3),
    DELETE (4);

    int value;
    CRUD(int value) {
        this.value = value;
    }

}
