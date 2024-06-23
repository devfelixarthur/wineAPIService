package com.api.wiveService.WineService.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValideBase64Util.Base64Validator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBase64 {
    String message() default "A string fornecida não é uma Base64 válida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
