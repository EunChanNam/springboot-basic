package org.prgms.voucherProgram.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private final DiscountPercent discountPercent;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        super(voucherId);
        this.discountPercent = new DiscountPercent(discountPercent);
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, long discountPercent) {
        super(voucherId, customerId);
        this.discountPercent = new DiscountPercent(discountPercent);
    }

    @Override
    public long discount(long beforeDiscount) {
        return discountPercent.discount(beforeDiscount);
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s%%", VoucherType.PERCENT_DISCOUNT, voucherId, discountPercent);
    }
}
