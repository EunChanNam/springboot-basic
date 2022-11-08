package org.prgrms.vouchermanagement.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final int discountAmount;
    private static final VoucherType voucherType = VoucherType.FIXED_AMOUNT;

    public FixedAmountVoucher(UUID uuid, int discountAmount) {
        this.voucherId = uuid;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return "바우처 종류 : " + voucherType.name() + ", ID : " + this.voucherId + ", 할인 가격 : " + this.discountAmount + "원";
    }
}
