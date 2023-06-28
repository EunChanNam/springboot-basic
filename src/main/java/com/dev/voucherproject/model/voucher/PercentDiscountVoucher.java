package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getDiscountNumber() {
        return this.percent;
    }

    @Override
    public VoucherDto conversionDto() {
        return VoucherDto.fromEntity(VoucherPolicy.PERCENT_DISCOUNT_VOUCHER, this);
    }
}
