package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Collection;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(
      VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public UUID create(VoucherMapper voucherMapper, long voucherDiscountData)
      throws VoucherException {
    var voucher = voucherMapper.getFactory().create(UUID.randomUUID(), voucherDiscountData);
    voucherRepository.save(voucher);
    return voucher.getVoucherId();

  }

  public Collection<Voucher> getAllVouchers() {
    return voucherRepository.getAllVouchers();
  }
}
