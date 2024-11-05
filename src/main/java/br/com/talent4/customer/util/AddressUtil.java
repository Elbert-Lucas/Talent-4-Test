package br.com.talent4.customer.util;

import lombok.Getter;

import java.util.Arrays;


public class AddressUtil {
    public static final String[] BRAZILIAN_STATES = {
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
        "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
        "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    };
    public static boolean isAValidState(String state){
        return Arrays.asList(AddressUtil.BRAZILIAN_STATES).contains(state.toUpperCase());
    }
}
