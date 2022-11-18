package com.program.commandLine.service;

import com.program.commandLine.voucher.FixedAmountVoucher;
import com.program.commandLine.voucher.PercentDiscountVoucher;
import com.program.commandLine.voucher.Voucher;
import com.program.commandLine.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(String stringVoucherType, UUID voucherId, int discount){

        VoucherType voucherType = VoucherType.of(stringVoucherType);
        Voucher newVoucher = switch (voucherType){
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, discount);
            case FIXED_AMOUNT_DISCOUNT ->  new FixedAmountVoucher(voucherId, discount);
        };
        return newVoucher;
    }
}
