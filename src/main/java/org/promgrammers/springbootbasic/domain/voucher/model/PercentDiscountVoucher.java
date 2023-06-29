package org.promgrammers.springbootbasic.domain.voucher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final long MIN_PERCENT = 0;
    private static final long MAX_PERCENT = 100;
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - ((beforeDiscount * this.getAmount()) / 100);
    }

    @Override
    protected void validateAmount(long discountAmount) {
        if (discountAmount <= MIN_PERCENT) {
            throw new IllegalArgumentException("할인율은 0보다 커야 합니다. => " + discountAmount);
        }
        if (discountAmount > MAX_PERCENT) {
            throw new IllegalArgumentException("할인율은 100보다 클 수 없습니다. => " + discountAmount);
        }
    }
}
