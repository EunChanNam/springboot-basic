package com.programmers.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {
    @DisplayName("고정 할인 바우처 discount() 메서드 성공 테스트")
    @ParameterizedTest
    @CsvSource(value = {"100, 1000, 900", "500, 1000, 500"})
    void discount(long voucherAmount, long originalPrice, long finalPrice) {
        Voucher voucher = FixedAmountVoucher.of(UUID.randomUUID(), voucherAmount);
        assertThat(voucher.discount(originalPrice)).isEqualTo(finalPrice);
    }

    @DisplayName("실제 금액보다 할인양이 클 경우 0원 반환 테스트")
    @ParameterizedTest
    @CsvSource(value = {"1001, 1000, 0", "2000, 1000, 0"})
    void discountTo0Won(long voucherAmount, long originalPrice, long finalPrice) {
        Voucher voucher = FixedAmountVoucher.of(UUID.randomUUID(), voucherAmount);
        assertThat(voucher.discount(originalPrice)).isEqualTo(finalPrice);
    }

    @DisplayName("생성할 고정 할인 바우처의 할인양이 범위안에 속하지 않을 경우 예외 발생 테스트")
    @ParameterizedTest
    @ValueSource(longs = {-1000, -1, 5001, 5002, 10000})
    void notIncludeDiscountAmountRange(long voucherAmount) {
        Assertions.assertThatThrownBy(() -> FixedAmountVoucher.of(UUID.randomUUID(), voucherAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1 ~ 5000 범위의 바우처 할인양을 입력해주세요");
    }

}
