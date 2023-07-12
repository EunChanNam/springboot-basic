package org.prgrms.kdt.wallet.dto.response;

import org.prgrms.kdt.wallet.domain.Wallet;

import java.util.UUID;

public record WalletResponse(UUID walletId, String memberName, String voucherType, double voucherAmount) {
    public WalletResponse(Wallet wallet) {
        this(wallet.getWalletId(),
                wallet.getMember().getMemberName().getName(),
                wallet.getVoucher().getVoucherType().getDescripton(),
                wallet.getVoucher().getDiscountPolicy().getAmount());
    }
}
