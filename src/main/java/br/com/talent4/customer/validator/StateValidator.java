package br.com.talent4.customer.validator;

import br.com.talent4.customer.annotation.State;
import br.com.talent4.customer.util.AddressUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class StateValidator implements ConstraintValidator<State, String>{

    @Override
    public boolean isValid(String state, ConstraintValidatorContext constraintValidatorContext) {
        if (state == null || state.isBlank()) return false;
        return AddressUtil.isAValidState(state);
    }
}
