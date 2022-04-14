package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final VoucherType type = VoucherType.FIXED;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "voucherId=" + voucherId +
            ", amount=" + amount +
            '}';
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - this.amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return this.type;
    }

}
