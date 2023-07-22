package org.prgrms.kdt.wallet.dao;

import org.prgrms.kdt.wallet.domain.JoinedWallet;
import org.prgrms.kdt.wallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    Wallet insert(Wallet wallet);

    List<JoinedWallet> findWithMemeberAndVoucherByMemberId(UUID memberId);

    List<JoinedWallet> findWithMemeberAndVoucherByVoucherId(UUID voucherId);

    void deleteById(UUID walletId);

    List<JoinedWallet> findWithMemeberAndVoucherAll();
}
