package com.api.wiveService.WineService.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Base64;

public class ValideBase64Util {

    public static class Base64Validator implements ConstraintValidator<ValidBase64, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.isEmpty()) {
                return true;
            }

            try {
                Base64.getDecoder().decode(value);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }
}
