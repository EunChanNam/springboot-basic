package com.demo.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final String UNIT = "원";

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public String getVoucherType() {
        return VoucherType.FIXED_AMOUNT.getVoucherDescription();
    }

    @Override
    public String getDiscountInfo() {
        return amount + UNIT;
    }
}
