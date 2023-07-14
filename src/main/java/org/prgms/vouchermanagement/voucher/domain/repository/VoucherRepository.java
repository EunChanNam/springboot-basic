package org.prgms.vouchermanagement.voucher.domain.repository;

import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);
}
