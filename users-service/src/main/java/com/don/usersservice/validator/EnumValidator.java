package com.don.usersservice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Donald Veizi
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
public @interface EnumValidator {

    String message() default "Must be one of the valid enum values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class < ? extends Enum < ? >> enumClass();

    boolean ignoreCase() default false;

}
