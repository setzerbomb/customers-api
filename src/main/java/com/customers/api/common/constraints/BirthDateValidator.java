package com.customers.api.common.constraints;

import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.cglib.core.Local;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BirthDateValidator implements ConstraintValidator<BirthDate,LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date != null)
        {
            LocalDate.now();
            return date.isAfter(LocalDate.now().minusYears(100)) && date.isBefore(LocalDate.now());
        }
        return false;
    }
}