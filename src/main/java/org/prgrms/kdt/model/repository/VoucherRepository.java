package org.prgrms.kdt.model.repository;

import java.util.List;
import java.util.Optional;

import org.prgrms.kdt.model.entity.VoucherEntity;

public interface VoucherRepository {

	VoucherEntity createVoucher(VoucherEntity voucherEntity);

	List<VoucherEntity> findAll();

	VoucherEntity updateVoucher(VoucherEntity voucherEntity);

	Optional<VoucherEntity> findById(Long voucherId);

	boolean deleteById(Long voucherId);
}
