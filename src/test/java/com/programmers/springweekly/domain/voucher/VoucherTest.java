package com.programmers.springweekly.domain.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoucherTest {

    @ParameterizedTest
    @CsvSource(value = {"1000:10000:9000", "2000:3000:1000", "10000:20000:10000"}, delimiter = ':')
    @DisplayName("고정 할인을 진행한다.")
    void proceedFixedDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new FixedAmountVoucher(discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        assertThat(discountPrice).isEqualTo(expectedDiscount);
    }

    @ParameterizedTest
    @CsvSource(value = {"30:9000:6300", "10:10000:9000", "50:20000:10000"}, delimiter = ':')
    @DisplayName("퍼센트 할인을 진행한다.")
    void proceedPercentDiscount(long discountAmount, long inputPrice, long expectedDiscount) {
        // given
        Voucher voucher = new PercentDiscountVoucher(discountAmount);

        // when
        long discountPrice = voucher.discount(inputPrice);

        // then
        assertThat(discountPrice).isEqualTo(expectedDiscount);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "!@#", "1입니다"})
    @DisplayName("고정 할인 바우처를 생성할 때 입력값이 숫자가 아니면 예외가 발생한다.")
    void fixedDiscountNumberException(String input) {
        assertThatThrownBy(() -> VoucherFactory.createVoucher(VoucherType.FIXED, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Is not a number");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "!@#", "1입니다"})
    @DisplayName("퍼센트 할인 바우처를 생성할 때 입력값이 숫자가 아니면 예외가 발생한다.")
    void percentDiscountNumberException(String input) {
        assertThatThrownBy(() -> VoucherFactory.createVoucher(VoucherType.PERCENT, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Is not a number");
    }

    @ParameterizedTest
    @ValueSource(strings = {"101"})
    @DisplayName("퍼센트 할인 바우처를 생성할 때 입력값이 0에서 100사이가 아닌 경우 예외가 발생한다.")
    void percentDiscountNumberRangeException(String input) {
        assertThatThrownBy(() -> VoucherFactory.createVoucher(VoucherType.PERCENT, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A number that is out of range");
    }
}
