package org.prgms.voucher.voucher;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
public class FixedAmountVoucher implements Voucher {

    private final long amount;
    private final UUID id;
    private final VoucherPolicy voucherPolicy = VoucherPolicy.FIXED_AMOUNT;

    public FixedAmountVoucher(long amount, UUID id) {
        this.amount = amount;
        this.id = id;
    }

    @Override
    public long discount(long price) {
        return Math.max(0, price - amount);
    }

}
