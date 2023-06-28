package me.kimihiqq.vouchermanagement.domain.voucher;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final String type;
    private final long discount;

    public FixedAmountVoucher(UUID voucherId, String type, long discountAmount) {
        validateDiscountAmount(discountAmount);
        this.voucherId = voucherId;
        this.type = "Fixed";
        this.discount = discountAmount;
    }

    private void validateDiscountAmount(long discountAmount) {
        if (discountAmount <= 0) {
            throw new IllegalArgumentException("Discount amount must be positive.");
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public long getDiscount() {
        return discount;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedPrice = Math.max(0, beforeDiscount - discount);
        log.info("Discount applied: {} - Price before discount: {}, Price after discount: {}", discount, beforeDiscount, discountedPrice);
        return discountedPrice;
    }
}