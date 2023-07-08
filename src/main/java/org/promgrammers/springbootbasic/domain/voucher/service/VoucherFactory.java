package org.promgrammers.springbootbasic.domain.voucher.service;

import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class VoucherFactory {

    private static final Logger logger = LoggerFactory.getLogger(VoucherFactory.class);

    private VoucherFactory() throws InstantiationException {
        logger.error("Cannot instantiate.");
        throw new InstantiationException("인스턴스화 할 수 없습니다.");
    }

    public static Voucher createVoucher(CreateVoucherRequest createVoucherRequest) {

        VoucherType voucherType = createVoucherRequest.getVoucherType();

        return convertToVoucher(UUID.randomUUID(), voucherType, createVoucherRequest.getDiscountAmount());
    }

    public static Voucher convertToVoucher(UUID voucherId, VoucherType voucherType, long amount) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(voucherId, amount);
            case PERCENT:
                return new PercentDiscountVoucher(voucherId, amount);
            default:
                logger.error("Invalid Voucher Type => {}", voucherType);
                throw new IllegalArgumentException("유효하지 않은 Voucher 요청입니다.");
        }
    }
}