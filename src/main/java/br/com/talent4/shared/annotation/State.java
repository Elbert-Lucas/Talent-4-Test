package br.com.talent4.shared.annotation;

import br.com.talent4.shared.validator.StateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String message() default "Invalid state";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}