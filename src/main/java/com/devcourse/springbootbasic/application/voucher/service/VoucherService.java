package com.devcourse.springbootbasic.application.voucher.service;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.update(voucher);
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher findVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return voucherRepository.findAllByCustomerId(customerId);
    }

    public void deleteAllVouchers() {
        voucherRepository.deleteAll();
    }

    public Voucher deleteVoucherById(UUID voucherId) {
        var foundVoucher = voucherRepository.findById(voucherId);
        voucherRepository.deleteById(voucherId);
        return foundVoucher.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

    public Voucher deleteVoucherCustomerByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        var foundVoucher = voucherRepository.findByCustomerIdAndVoucherId(customerId, voucherId);
        voucherRepository.deleteByCustomerIdAndVoucherId(customerId, voucherId);
        return foundVoucher.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()));
    }

}