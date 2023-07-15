package com.prgrms.model.wallet;

public class Wallet {

    private final int customerId;
    private final int voucherId;
    private final int walletId;
    private boolean deleted = false;

    public Wallet(int walletId, int customerId, int voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public boolean deleted() {
        return deleted = true;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public int getWalletId() {
        return walletId;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
