package org.othertwink.onlineshop.exception.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.othertwink.onlineshop.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.StringJoiner;

@Component
public class ValidationUtil {

    @Autowired
    private Validator validator;

    public <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringJoiner errors = new StringJoiner("; ");
            for (ConstraintViolation<T> violation : violations) {
                errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new ValidationException(errors.toString());
        }
    }
}
