package com.example.voucher.domain;

import static com.example.voucher.utils.ExceptionMessage.*;

import java.util.UUID;

import com.example.voucher.constant.VoucherType;

public interface Voucher {

    UUID getVoucherId();

    Long getValue();

    VoucherType getVoucherType();

    long discount(long beforeAmount);

    default void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
