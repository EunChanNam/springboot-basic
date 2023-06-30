package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.VoucherException;

public class PercentDiscountPolicy implements VoucherPolicy {

    private static final long MIN_PERCENT = 0;
    private static final long MAX_PERCENT = 100;

    private final long amount;

    public PercentDiscountPolicy(long amount) {
        validate(amount);
        this.amount = amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (beforeDiscount * amount / 100);
    }

    protected void validate(long amount) {
        if (amount <= MIN_PERCENT || amount > MAX_PERCENT) {
            throw new VoucherException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        }
    }
}

