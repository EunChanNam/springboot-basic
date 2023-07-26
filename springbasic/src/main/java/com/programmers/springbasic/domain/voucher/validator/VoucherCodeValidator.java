package com.programmers.springbasic.domain.voucher.validator;

import lombok.Getter;

@Getter
public class VoucherCodeValidator {
    private static final String VALID_VOUCHER_CODE_REGEXP = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    private static final String INVALID_VOUCHER_CODE_MESSAGE = "유효하지 않은 voucher code 형식입니다.";

    public static void validateVoucherCode(String inputVoucherCode) {
        if (!inputVoucherCode.matches(VALID_VOUCHER_CODE_REGEXP)) {
            throw new IllegalArgumentException(INVALID_VOUCHER_CODE_MESSAGE);
        }
    }
}
