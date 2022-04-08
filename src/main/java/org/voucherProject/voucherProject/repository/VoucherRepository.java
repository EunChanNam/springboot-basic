package org.voucherProject.voucherProject.repository;

import org.voucherProject.voucherProject.entity.voucher.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId) throws IOException;

    Voucher save(Voucher voucher) throws IOException;

    List<Voucher> findAll() throws IOException;
}
