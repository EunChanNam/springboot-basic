package com.prgrms.commandLineApplication.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {

  Voucher percentDiscountVoucher;

  @ParameterizedTest
  @CsvSource(value = {"20|80000", "30|70000", "50|50000"}, delimiter = '|')
  @DisplayName("percentDiscountVoucher 할인 계산 결과 일치 테스트")
  void percentDiscountVoucher_계산_성공(double discountValue, double result) {
    String voucherType = "percent";
    percentDiscountVoucher = new PercentDiscountVoucher(voucherType, discountValue);
    assertThat(percentDiscountVoucher.discount(100000)).isEqualTo(result);
  }

  @ParameterizedTest
  @CsvSource(value = {"101|0, 200|0, 220|0"}, delimiter = '|')
  @DisplayName("percentDiscountVoucher 할인 계산 결과 불일치 테스트")
  void percentDiscountVoucher_계산_실패(double discountValue, double result) {
    String voucherType = "percent";
    percentDiscountVoucher = new PercentDiscountVoucher(voucherType, discountValue);
    assertThat(percentDiscountVoucher.discount(100000)).isEqualTo(result);
  }

}
