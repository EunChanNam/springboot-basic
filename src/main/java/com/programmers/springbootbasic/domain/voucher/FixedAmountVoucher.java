package com.programmers.springbootbasic.domain.voucher;

import com.programmers.springbootbasic.domain.model.Duration;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final long ZERO = 0L;
    private static final int MIN_AMOUNT = 10;
    private static final int MAX_AMOUNT = 10_000_000;
    private static final String INVALID_AMOUNT = String.format(
            "잘못된 할인 금액입니다. 할인 금액은 최소 %d원부터 최대 %d원까지 설정 가능합니다. 현재 입력 금액: ", MIN_AMOUNT, MAX_AMOUNT
    );

    private final int amount;

    public FixedAmountVoucher(UUID voucherId, VoucherType voucherType, String name, Duration duration, int amount) {
        this(voucherId, voucherType, name, ZERO, duration, amount);
    }

    public FixedAmountVoucher(UUID voucherId, VoucherType voucherType, String name, Long minimumPriceCondition, Duration duration, int amount) {
        super(voucherId, voucherType, name, minimumPriceCondition, duration);
        if (isInvalidAmount(amount)) {
            throw new IllegalArgumentException(INVALID_AMOUNT + String.format("%d원", amount));
        }
        this.amount = amount;
    }

    @Override
    public Long getDiscountPrice(Long priceBeforeDiscount) {
        if (priceBeforeDiscount <= amount) {
            return ZERO;
        }
        return priceBeforeDiscount - amount;
    }

    private boolean isInvalidAmount(int amount) {
        return amount < MIN_AMOUNT || MAX_AMOUNT < amount;
    }
}
