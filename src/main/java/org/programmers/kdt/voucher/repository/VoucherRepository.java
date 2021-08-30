package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    default void deleteVoucher(Voucher voucher) {
        deleteVoucher(voucher.getVoucherId());
    }
    void deleteVoucher(UUID voucherId);
    List<Voucher> findAll();
}
