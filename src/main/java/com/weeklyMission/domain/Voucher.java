package com.weeklyMission.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getAmount();

    long discount(long beforeDiscount);
}
