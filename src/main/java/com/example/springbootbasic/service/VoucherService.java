package com.example.springbootbasic.service;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(Voucher voucher) {
        logger.info("Success - {}", voucher.toString());
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        logger.info("Success - findAllVouchers");
        return voucherRepository.findAllVouchers();
    }

    public void deleteAllVouchers() {
        logger.info("Success - deleteAllVouchers");
        voucherRepository.deleteAll();
    }
}
