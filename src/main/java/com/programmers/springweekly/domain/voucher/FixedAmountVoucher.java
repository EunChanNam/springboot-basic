package com.programmers.springweekly.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final long fixedDiscountAmount;

    public FixedAmountVoucher(UUID voucherId, long fixedDiscountAmount) {
        this.voucherId = voucherId;
        this.fixedDiscountAmount = fixedDiscountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - fixedDiscountAmount;
    }

    @Override
    public long getVoucherAmount() {
        return fixedDiscountAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }
}
