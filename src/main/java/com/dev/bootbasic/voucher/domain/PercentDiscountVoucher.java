package com.dev.bootbasic.voucher.domain;

import java.util.UUID;

import static com.dev.bootbasic.voucher.domain.VoucherType.PERCENT;

public class PercentDiscountVoucher extends Voucher {

    public static final int PERCENT_MINIMUM_DISCOUNT_AMOUNT = 1;
    public static final int PERCENT_MAXIMUM_DISCOUNT_AMOUNT = 100;
    public static final String PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE = "백분율할인 바우처는" + PERCENT_MINIMUM_DISCOUNT_AMOUNT + "이상" + PERCENT_MAXIMUM_DISCOUNT_AMOUNT + "이하만 가능합니다.";
    private static final int MAX_PERCENT = 100;

    private PercentDiscountVoucher(UUID id, VoucherType voucherType, int discountAmount) {
        super(id, voucherType, discountAmount);
    }

    public static PercentDiscountVoucher of(UUID id, int discountAmount) {
        validateAmount(discountAmount);
        return new PercentDiscountVoucher(id, PERCENT, discountAmount);
    }

    private static void validateAmount(int discountAmount) {
        if (discountAmount < PERCENT_MINIMUM_DISCOUNT_AMOUNT || PERCENT_MAXIMUM_DISCOUNT_AMOUNT < discountAmount) {
            throw new IllegalArgumentException(PERCENT_DISCOUNT_AMOUNT_VALIDATION_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public int discount(int originPrice) {
        return originPrice - (originPrice * getDiscountAmount() / MAX_PERCENT);
    }

}
