package com.example.demo.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    String getName();
    long getValue();
}
