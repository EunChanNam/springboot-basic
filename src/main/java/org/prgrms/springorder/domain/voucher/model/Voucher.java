package org.prgrms.springorder.domain.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Voucher {

    private final UUID voucherId;

    private final long amount;

    private final LocalDateTime createdAt;

    public UUID getVoucherId() {
        return voucherId;
    }

    protected Voucher(UUID voucherId, long amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    protected abstract void validateAmount(long amount);

    public abstract VoucherType getVoucherType();

    public abstract long discount(long beforeDiscount);

    @Override
    public String toString() {
        return String.format("voucherType = %s, id = %s, amount = %s, createdAt = %s", getVoucherType(), getVoucherId(), getAmount(), getCreatedAt());
    }

}
