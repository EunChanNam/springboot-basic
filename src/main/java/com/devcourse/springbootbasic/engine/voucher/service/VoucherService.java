package com.devcourse.springbootbasic.engine.voucher.service;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public <T> List<T> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
