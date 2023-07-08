package com.example.voucher.utils;

import static com.example.voucher.utils.ExceptionMessage.MESSAGE_ERROR_NON_ZERO_CONSTRAINT;
import static com.example.voucher.utils.ExceptionMessage.FORMAT_ERROR_GREATER_THAN_CONSTRAINT;

public class Validator {

    private Validator() {
    }

    public static void validateNonZero(long value) {
        if (value == 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_NON_ZERO_CONSTRAINT);
        }
    }

    public static void validateGreaterThan(long value, long threshold) {
        if (value <= threshold) {
            throw new IllegalArgumentException(
                String.format("{} {}", FORMAT_ERROR_GREATER_THAN_CONSTRAINT, threshold));
        }
    }

}
