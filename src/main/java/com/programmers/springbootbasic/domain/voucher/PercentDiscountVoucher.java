package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final int percent;
    static int MIN_PERCENT = 1;
    static int MAX_PERCENT = 100;

    public PercentDiscountVoucher(UUID voucherId, String name, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, expirationDate);
        if (isInvalidRange(percent)) {
            throw new IllegalArgumentException("잘못된 할인 범위");
        }
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate, int percent) {
        super(voucherId, name, minimumPriceCondition, expirationDate);
        if (isInvalidRange(percent)) {
            throw new IllegalArgumentException("잘못된 할인 범위");
        }
        this.percent = percent;
    }

    @Override
    public Long getDiscountPrice(Long priceBeforeDiscount) {
        int tenForRound = 10;
        double discountAmount = priceBeforeDiscount * percent / (double) MAX_PERCENT;
        double discountedPrice = priceBeforeDiscount - discountAmount;
        return Math.round((discountedPrice) / tenForRound) * tenForRound;
    }

    @Override
    public Long discount(Long priceBeforeDiscount) {
        setVoucherState(VoucherState.USED);
        return getDiscountPrice(priceBeforeDiscount);
    }

    private boolean isInvalidRange(int percent) {
        return percent < MIN_PERCENT || MAX_PERCENT < percent;
    }
}
