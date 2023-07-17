package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher(VoucherCreationRequest request) {
        voucherService.createVoucher(request);
    }

    public List<VoucherResponse> getVouchers() {
        return voucherService.getVouchers();
    }

    public void updateVoucher(UUID id, VoucherUpdateRequest request) {
        voucherService.updateVoucher(id, request);
    }

    public void deleteVoucher(UUID id) {
        voucherService.deleteVoucher(id);
    }
}
