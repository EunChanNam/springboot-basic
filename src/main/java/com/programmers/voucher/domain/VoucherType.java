package com.programmers.voucher.domain;

import com.programmers.exception.InvalidRequestValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public enum VoucherType {
    FixedAmountVoucher("1", "fixedamountvoucher", com.programmers.voucher.domain.FixedAmountVoucher::new),
    PercentDiscountVoucher("2", "percentdiscountvoucher", com.programmers.voucher.domain.PercentDiscountVoucher::new);

    private static final Logger log = LoggerFactory.getLogger(VoucherType.class);

    private final String number;
    private final String name;
    private final VoucherTypeFunction<UUID, String, Long, Voucher> voucherConstructor;

    VoucherType(String number, String name, VoucherTypeFunction<UUID, String, Long, Voucher> voucherConstructor) {
        this.number = number;
        this.name = name;
        this.voucherConstructor = voucherConstructor;
    }

    public static VoucherType findVoucherType(String input) {
        checkVoucherTypeEmpty(input);

        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.number, input) || Objects.equals(voucherType.name, input))
                .findAny()
                .orElseThrow(() -> {
                    log.error("the invalid voucher type found. voucher type value = {}", input);
                    return new InvalidRequestValueException("[ERROR] 요청하신 voucher type 값이 유효하지 않습니다.");
                });
    }

    private static void checkVoucherTypeEmpty(String voucherType) {
        if (voucherType.isEmpty()) {
            log.error("The voucher type not found.");
            throw new InvalidRequestValueException("[ERROR] voucher type 값이 비었습니다.");
        }
    }

    public static Voucher createVoucher(String voucherTypeInput, String voucherName, Long discountValue) {
        VoucherType voucherType = findVoucherType(voucherTypeInput.toLowerCase());
        return voucherType.makeVoucher(voucherName, discountValue);
    }

    public static Voucher createVoucher(String voucherTypeInput, UUID uuid, String voucherName, Long discountValue) {
        VoucherType voucherType = findVoucherType(voucherTypeInput.toLowerCase());
        return voucherType.makeVoucher(uuid, voucherName, discountValue);
    }

    private Voucher makeVoucher(String voucherName, Long discountValue) {
        return this.voucherConstructor.apply(UUID.randomUUID(), voucherName, discountValue);
    }

    private Voucher makeVoucher(UUID uuid, String voucherName, Long discountValue) {
        return this.voucherConstructor.apply(uuid, voucherName, discountValue);
    }
}
