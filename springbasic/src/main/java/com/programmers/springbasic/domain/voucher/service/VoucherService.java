package com.programmers.springbasic.domain.voucher.service;

import com.programmers.springbasic.domain.voucher.dto.request.VoucherCreateRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.request.VoucherUpdateRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.response.VoucherResponseDTO;
import com.programmers.springbasic.domain.voucher.factory.VoucherFactory;
import com.programmers.springbasic.domain.voucher.repository.VoucherRepository;
import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public void createVoucher(VoucherCreateRequestDTO voucherCreateRequestDTO) {
        Voucher voucher = VoucherFactory.issueVoucher(voucherCreateRequestDTO);

        voucherRepository.save(voucher);
    }

    public List<VoucherResponseDTO> getAllVoucherInfo(String voucherType) {
        List<Voucher> vouchers = voucherRepository.findAllByVoucherType(VoucherType.of(voucherType));

        return vouchers.stream()
                .map(VoucherResponseDTO::toResponse)
                .collect(Collectors.toList());
    }

    public VoucherResponseDTO findVoucher(String voucherCode) {
        Voucher voucher = voucherRepository.findByCode(UUID.fromString(voucherCode))
                .orElseThrow(() -> new RuntimeException("조회하고자 하는 voucher가 없습니다."));

        return VoucherResponseDTO.toResponse(voucher);
    }

    public void updateVoucher(VoucherUpdateRequestDTO voucherUpdateRequestDTO) {
        Voucher voucher = voucherRepository.findByCode(UUID.fromString(voucherUpdateRequestDTO.getVoucherCode()))
                .orElseThrow(() -> new RuntimeException("조회하고자 하는 voucher가 없습니다."));

        voucher.updateValue(voucherUpdateRequestDTO.getValue());

        voucherRepository.update(voucher);
    }

    public void removeVoucher(String inputVoucherCode) {
        UUID voucherCode = UUID.fromString(inputVoucherCode);

        voucherRepository.delete(voucherCode);
    }
}
