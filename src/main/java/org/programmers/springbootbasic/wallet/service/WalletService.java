package org.programmers.springbootbasic.wallet.service;

import org.programmers.springbootbasic.customer.model.Customer;
import org.programmers.springbootbasic.exception.DuplicateObjectKeyException;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.wallet.domain.Wallet;
import org.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Voucher> getVouchers(UUID customerId) {
        return walletRepository.findVoucherByCustomerId(customerId);
    }

    public List<Customer> getCustomers(UUID voucherId) {
        return walletRepository.findCustomerByVoucherId(voucherId);
    }

    public Wallet allocateVoucher(Wallet wallet) {
        if (checkWalletExist(wallet.getWalletId())) {
            throw new DuplicateObjectKeyException("월렛 키 중복입니다.");
        }
        return walletRepository.insert(wallet);
    }

    public void deleteWallet(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    private boolean checkWalletExist(UUID walletId) {
        return walletRepository.getCountByWalletId(walletId) > 0;
    }
}
