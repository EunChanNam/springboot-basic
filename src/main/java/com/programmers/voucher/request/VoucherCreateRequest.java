package com.programmers.voucher.request;

import com.programmers.voucher.enumtype.VoucherType;

public class VoucherCreateRequest {
    private final VoucherType voucherType;
    private final int amount;

    public VoucherCreateRequest(VoucherType type, int amount) {
        this.voucherType = type;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getAmount() {
        return amount;
    }
}
