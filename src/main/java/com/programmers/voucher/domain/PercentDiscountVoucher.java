package com.programmers.voucher.domain;

import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private static final int MAX_DISCOUNT_AMOUNT = 100;
    private static final int MIN_DISCOUNT_AMOUNT = 0;

    private final UUID voucherId;
    private final long discountAmount;

    protected PercentDiscountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    public static Voucher of(UUID voucherId, long discountAmount) {
        validateVoucherId(voucherId);
        validateDiscountAmount(discountAmount);
        return new PercentDiscountVoucher(voucherId, discountAmount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public long discount(long originPrice) {
        return originPrice - (originPrice * getDiscountAmount() / MAX_DISCOUNT_AMOUNT);
    }

    private static void validateVoucherId(UUID voucherId) {
        if (Objects.isNull(voucherId)) {
            throw new IllegalArgumentException("바우처 아이디가 비어있습니다.");
        }
    }

    private static void validateDiscountAmount(long discountAmount) {
        if (discountAmount > MAX_DISCOUNT_AMOUNT || discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(MIN_DISCOUNT_AMOUNT + " ~ " + MAX_DISCOUNT_AMOUNT + " 범위의 바우처 할인양을 입력해주세요. " + "입력값: " + discountAmount);
        }
    }
}
