package org.prgrms.kdtspringdemo.voucher.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {
    FIXED,
    PERCENT;

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final String CANT_FIND_VOUCHER_TYPE = "알맞는 바우처 형식이 없습니다.";

    public static VoucherType findVoucherType(String userVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(userVoucherType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userVoucherType, CANT_FIND_VOUCHER_TYPE);
                    throw new IllegalArgumentException(CANT_FIND_VOUCHER_TYPE);
                });
    }
}
