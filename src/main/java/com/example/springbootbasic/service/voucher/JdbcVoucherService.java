package com.example.springbootbasic.service.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.repository.voucher.JdbcVoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JdbcVoucherService {
    private final JdbcVoucherRepository voucherRepository;

    public JdbcVoucherService(JdbcVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAllVouchers();
    }

    public List<Voucher> findAllVoucherByVoucherType(VoucherType voucherType) {
        return voucherRepository.findAllVouchersByVoucherType(voucherType);
    }

    public List<Voucher> findVouchersBy(Long voucherId,
                                        VoucherType voucherType,
                                        LocalDateTime findStartAt,
                                        LocalDateTime findEndAt
    ) {
        return voucherRepository.findVouchersBy(voucherId, voucherType, findStartAt, findEndAt);
    }

    public Optional<Voucher> findById(long voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public Voucher update(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAllVouchers();
    }

    public void deleteVouchersByVoucherType(VoucherType voucherType) {
        voucherRepository.deleteVouchersByVoucherType(voucherType);
    }

    public boolean deleteVoucherById(long voucherId) {
        return voucherRepository.deleteVoucherById(voucherId);
    }
}
