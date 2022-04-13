package org.programmers.springbootbasic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.Voucher;
import org.programmers.springbootbasic.voucher.VoucherProperty;
import org.programmers.springbootbasic.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherProperty voucherProperty;

    @Override
    public Voucher registerVoucher(Voucher voucher) throws IllegalArgumentException {
        if (isValidAmount(
                voucher.getAmount(), VoucherType.findTypeByClass(voucher.getClass()))) {
            return voucherRepository.insert(voucher);
        }
        log.error("Illegal value of voucher's discount amount={}", voucher.getAmount());
        throw new IllegalArgumentException("Illegal value of voucher's discount amount=" + voucher.getAmount());
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(
                () -> new IllegalArgumentException("No voucher found")
        );
    }

    @Override
    public long applyVoucher(long beforeDiscount, Voucher voucher) {
        return voucher.discount(beforeDiscount);
    }

    @Override
    public void useVoucher(UUID voucherId) {
        voucherRepository.remove(voucherId);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public boolean isValidAmount(int amount, VoucherType voucherType) {
        int minimum;
        int maximum;

        switch (voucherType) {
            case FIXED:
                minimum = voucherProperty.getFixed().minimumAmount();
                maximum = voucherProperty.getFixed().maximumAmount();
                break;
            case RATE:
                minimum = voucherProperty.getRate().minimumAmount();
                maximum = voucherProperty.getRate().maximumAmount();
                break;
            default:
                log.error("Invalid voucherType={}", voucherType);
                throw new IllegalArgumentException("Invalid voucherType=" + voucherType);
        }
        return (amount >= minimum && amount <= maximum);
    }
}
