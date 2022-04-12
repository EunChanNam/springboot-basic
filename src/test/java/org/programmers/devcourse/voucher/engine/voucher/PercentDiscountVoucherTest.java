package org.programmers.devcourse.voucher.engine.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.devcourse.voucher.engine.exception.VoucherDataOutOfRangeException;

class PercentDiscountVoucherTest {

  @DisplayName("등록되어 있는 비율 만큼 가격을 할인해야 한다.")
  @ParameterizedTest
  @CsvSource(value = {
      "1000,10,900",
      "10000, 50, 5000"
  })
  void discount_price_by_percent_in_normal_situation(long beforeDiscount, long discountDegree,
      long afterDiscount) throws VoucherDataOutOfRangeException {
    Voucher voucher0 = PercentDiscountVoucher.factory.create(UUID.randomUUID(), discountDegree);
    assertThat(voucher0.discount(beforeDiscount)).isEqualTo(afterDiscount);
  }


  @DisplayName("할인 비율이 1~100 범위에 들어가지 않는 경우 VoucherDataOutOfRangeException을 던진다.")
  @ParameterizedTest
  @CsvSource(value = {
      "-5000",
      "500000",
      "44444",
      "0"
  })
  void throw_exception_when_discount_degree_is_not_in_1_to_100(long discountDegree) {
    assertThatThrownBy(() -> {
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), discountDegree);
    }).isInstanceOf(VoucherDataOutOfRangeException.class);
  }

  @DisplayName("할인 비율이 100일 경우 0을 반환해야 한다.")
  @ParameterizedTest
  @CsvSource(value = {
      "100000,100",
      "99999,100"
  })
  void return_zero_when_discount_degree_is_100(long beforeDiscount,
      long discountDegree) throws VoucherDataOutOfRangeException {
    long discountedPrice = PercentDiscountVoucher.factory.create(UUID.randomUUID(), discountDegree)
        .discount(beforeDiscount);

    assertThat(discountedPrice).isZero();
  }
}
