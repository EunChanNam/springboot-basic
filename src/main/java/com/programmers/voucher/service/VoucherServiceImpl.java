package com.programmers.voucher.service;

import com.programmers.voucher.domain.VoucherPolicy;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID create(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = toEntity(UUID.randomUUID(), voucherRequestDto);
        return voucherRepository.save(voucher);
    }

    public List<VoucherResponseDto> findVouchers() {
        return voucherRepository.findAll();
    }

    private Voucher toEntity(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        return VoucherPolicy.findByCommand(voucherRequestDto.getVoucherType())
                .constructor(voucherId, voucherRequestDto.getDiscountAmount(), LocalDate.now());
    }
}
