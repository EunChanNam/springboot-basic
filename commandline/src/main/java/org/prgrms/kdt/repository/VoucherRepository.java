package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}
