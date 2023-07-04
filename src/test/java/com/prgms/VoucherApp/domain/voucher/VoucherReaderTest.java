package com.prgms.VoucherApp.domain.voucher;

import com.prgms.VoucherApp.domain.voucher.model.VoucherReader;
import com.prgms.VoucherApp.domain.voucher.storage.VoucherStorage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class VoucherReaderTest {

    @Autowired
    VoucherReader voucherReader;
    @Autowired
    VoucherStorage voucherStorage;

    @Test
    @DisplayName("voucher 목록 출력")
    void findVoucherListTest() {
        // given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000), VoucherType.FIXED_VOUCHER);
        Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT_VOUCHER);
        voucherStorage.save(fixedVoucher);
        voucherStorage.save(percentVoucher);

        // when
        List<Voucher> findVouchers = voucherStorage.findAll();

        // then
        Assertions.assertThat(findVouchers).contains(fixedVoucher, percentVoucher);
    }

    @Test
    @DisplayName("VoucherId를 사용하여 1개만 출력")
    void findByVoucherIdTest() {
        // given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), BigDecimal.valueOf(1000), VoucherType.FIXED_VOUCHER);

        // when
        voucherStorage.save(fixedVoucher);
        Voucher findVoucher = voucherReader.readOne(fixedVoucher.getVoucherId());

        // then
        Assertions.assertThat(findVoucher).isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("VoucherId를 사용하여 검색 시 찾을 수 없는 예외 테스트")
    void findByVoucherIdExceptionTest() {
        // given
        UUID uuid = UUID.randomUUID();

        // when, then
        Assertions.assertThatThrownBy(() -> voucherReader.readOne(uuid))
                .hasMessageContaining("Voucher Id + " + uuid + " does not exist");
    }
}