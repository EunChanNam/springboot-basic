package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.repository.voucher.VoucherRepository;
import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.VoucherFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public List<Voucher> findAllVouchers() {
    return voucherRepository.findAll();
  }

  public Voucher findById(UUID id) {
    return voucherRepository.findById(id);
  }

  public void create(String voucherType, int discountAmount) {
    Voucher createdVoucher = VoucherFactory.of(UUID.randomUUID(), voucherType, discountAmount);
    voucherRepository.save(createdVoucher);
  }

}
