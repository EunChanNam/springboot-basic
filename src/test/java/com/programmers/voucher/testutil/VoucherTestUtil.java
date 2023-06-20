package com.programmers.voucher.testutil;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;

import java.util.UUID;

public class VoucherTestUtil {
    public static Voucher createFixedVoucher(UUID voucherId, int amount) {
        VoucherCreateRequest request = new VoucherCreateRequest("fixed", amount);
        return new FixedAmountVoucher(voucherId, request);
    }

    public static Voucher createPercentVoucher(UUID voucherId, int amount) {
        VoucherCreateRequest request = new VoucherCreateRequest("percent", amount);
        return new PercentDiscountVoucher(voucherId, request);
    }
}
