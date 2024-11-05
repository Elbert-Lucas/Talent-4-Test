package br.com.talent4.shared.validator;

import br.com.talent4.shared.annotation.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class StateValidator implements ConstraintValidator<State, String>{

    private final String[] BRAZILIAN_STATES = {
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
            "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
            "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

    @Override
    public boolean isValid(String state, ConstraintValidatorContext constraintValidatorContext) {
        if (state == null || state.isBlank()) return false;
        return Arrays.asList(BRAZILIAN_STATES).contains(state.toUpperCase());
    }
}
