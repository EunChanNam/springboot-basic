package com.programmers.global.exception;

public class VoucherExpirationException extends RuntimeException {
    private static final String MESSAGE = "[ERROR] 유효기간이 만료된 Voucher 입니다.";

    public VoucherExpirationException() {
        super(MESSAGE);
    }
}
