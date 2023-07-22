package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.global.exception.InvalidInputException;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public enum VoucherType {
    FIXED(1, "FIXED", FixedDiscountPolicy::new),
    PERCENT(2, "PERCENT", PercentDiscountPolicy::new);

    private final int number;
    private final String descripton;

    private final Function<Double, DiscountPolicy> function;

    VoucherType(int number, String descripton, Function<Double, DiscountPolicy> function) {
        this.number = number;
        this.descripton = descripton;
        this.function = function;
    }

    public static VoucherType getTypeByNum(String str) {
        int curNumber = Integer.parseInt(str);
        return Arrays.stream(VoucherType.values())
                .filter((e) -> e.number == curNumber)
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("입력한 바우처 타입은 지원하지 않습니다."));
    }

    public static VoucherType getTypeByStr(String str) {
        return Arrays.stream(VoucherType.values())
                .filter((e) -> Objects.equals(e.descripton, str))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("입력한 바우처 타입은 지원하지 않습니다."));
    }

    public String getDescripton() {
        return descripton;
    }

    public DiscountPolicy createPolicy(double amount) {
        return function.apply(amount);
    }
}
