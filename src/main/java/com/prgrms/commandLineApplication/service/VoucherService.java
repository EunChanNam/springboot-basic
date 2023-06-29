package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.repository.VoucherRepository;
import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.VoucherFactory;

import java.util.List;

public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public List<Voucher> findAllVouchers() {
    return voucherRepository.findAll();
  }

  public void create(String voucherType, int discountAmount) {
    Voucher createdVoucher = VoucherFactory.createVoucher(voucherType, discountAmount);
    voucherRepository.save(createdVoucher);
  }

}
