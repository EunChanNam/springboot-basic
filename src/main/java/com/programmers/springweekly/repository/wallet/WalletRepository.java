package com.programmers.springweekly.repository.wallet;

import com.programmers.springweekly.domain.wallet.Wallet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    Optional<Wallet> findByWalletId(UUID walletId);

    Optional<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    void deleteByWalletId(UUID walletId);

    List<Wallet> findAll();

}
