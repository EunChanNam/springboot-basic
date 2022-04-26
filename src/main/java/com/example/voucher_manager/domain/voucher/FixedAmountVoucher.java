package com.example.voucher_manager.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final Long discountPrice;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, Long discountPrice, VoucherType voucherType) {
        super(voucherId, null);
        this.discountPrice = discountPrice;
        this.voucherType = voucherType;
    }

    public FixedAmountVoucher(UUID voucherId, Long discountPrice, VoucherType voucherType, UUID ownerId) {
        super(voucherId, ownerId);
        this.discountPrice = discountPrice;
        this.voucherType = voucherType;
    }

    @Override
    public String toString() {
        return "[VoucherType : FixedAmountVoucher," + " VoucherId : " + voucherId + ", DiscountPrice : " + discountPrice + "$]";
    }

    @Override
    public Long discount(Long regularPrice) {
        return regularPrice - discountPrice;
    }

    @Override
    public void provideToCustomer(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public Long getDiscountInformation() {
        return discountPrice;
    }

}
