package com.programmers.springvoucherservice.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    String toString();

    long getValue();
}
