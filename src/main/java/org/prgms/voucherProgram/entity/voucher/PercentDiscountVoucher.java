package org.prgms.voucherProgram.entity.voucher;

import java.util.UUID;

import org.prgms.voucherProgram.exception.WrongDiscountPercentException;

public class PercentDiscountVoucher implements Voucher {
    private static final long MIN_PERCENT = 1;
    private static final long MAX_PERCENT = 100;

    private final UUID voucherId;
    private final long discountPercent;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) throws WrongDiscountPercentException {
        validateDiscountPercent(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    private void validateDiscountPercent(long discountPercent) throws WrongDiscountPercentException {
        if (isWrongPercent(discountPercent)) {
            throw new WrongDiscountPercentException();
        }
    }

    private boolean isWrongPercent(long discountPercent) {
        return MAX_PERCENT < discountPercent || discountPercent < MIN_PERCENT;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount * (1 - (discountPercent / 100.0)));
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d%%", VoucherType.PERCENT_DISCOUNT, voucherId, discountPercent);
    }
}
