package com.programmers.voucher.domain;

import java.time.LocalDate;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    Discount getDiscount();

    LocalDate getCreatedDate();

    LocalDate getExpirationDate();

    long discount(long beforeDiscount);

    boolean available();
}
