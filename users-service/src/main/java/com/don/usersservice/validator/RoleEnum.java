package com.don.usersservice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * @author Donald Veizi
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RoleEnumValidator.class)
public @interface RoleEnum {

    String message() default "Roles, must be any of the specified values!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class < ? extends Enum < ? >> enumClass();

    boolean ignoreCase() default false;

}
