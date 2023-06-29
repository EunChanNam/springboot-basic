package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.InvalidInputException;

import java.util.Arrays;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher"),
    PERCENT(2, "PercentDiscountVoucher");

    private final int number;
    private final String name;

    VoucherType(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public static VoucherType getType(String str) {
        int curNumber = Integer.parseInt(str);
        return Arrays.stream(VoucherType.values())
                .filter((e) -> e.number == curNumber)
                .findFirst()
                .orElseThrow(InvalidInputException::new);
    }

    public String getName() {
        return name;
    }
}
