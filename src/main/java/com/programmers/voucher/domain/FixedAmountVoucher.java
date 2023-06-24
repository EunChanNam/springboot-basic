package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;
import java.time.LocalDate;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final static String VOUCHER_ID_NULL_MESSAGE = "Voucher ID can not be null";
    private final static int EXPIRATION_POLICY = 7;
    private final UUID voucherId;
    private final Discount discountPercent;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;

    public FixedAmountVoucher(UUID voucherId, long percent, LocalDate localDate) {
        this.voucherId = validateVoucherId(voucherId);
        this.discountPercent = new DiscountPercent(percent);
        this.createdDate = localDate;
        this.expirationDate = expirationDate();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Discount getDiscount() {
        return discountPercent;
    }

    @Override
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (available()) {
            return discountPercent.discount(beforeDiscount);
        }
        throw new AmountValueException();
    }

    public boolean available() {
        if (LocalDate.now().isAfter(expirationDate)) {
            return false;
        }
        return true;
    }

    public LocalDate expirationDate() {
        return createdDate.plusDays(EXPIRATION_POLICY);
    }

    private UUID validateVoucherId(UUID voucherId) {
        if (voucherId == null) throw new NullPointerException(VOUCHER_ID_NULL_MESSAGE);
        return voucherId;
    }
}
