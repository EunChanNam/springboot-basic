package com.programmers.springweekly.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.programmers.springweekly.domain.voucher.FixedAmountVoucher;
import com.programmers.springweekly.domain.voucher.PercentDiscountVoucher;
import com.programmers.springweekly.domain.voucher.Voucher;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class JdbcTemplateVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처를 저장한다.")
    void save() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucherExpected1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucherExpected2 = new PercentDiscountVoucher(voucherId2, 30);

        // when
        voucherRepository.save(voucherExpected1);
        voucherRepository.save(voucherExpected2);
        Voucher voucherActual1 = voucherRepository.findById(voucherId1);
        Voucher voucherActual2 = voucherRepository.findById(voucherId2);

        // then
        assertThat(voucherActual1).usingRecursiveComparison().isEqualTo(voucherExpected1);
        assertThat(voucherActual2).usingRecursiveComparison().isEqualTo(voucherExpected2);
    }

    @Test
    @DisplayName("바우처를 업데이트 한다.")
    void update() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucherExpected1 = new PercentDiscountVoucher(voucher.getVoucherId(), 30);

        // when
        voucherRepository.save(voucher);
        voucherRepository.update(voucherExpected1);

        Voucher voucherActual1 = voucherRepository.findById(voucher.getVoucherId());

        // then
        assertThat(voucherActual1).usingRecursiveComparison().isEqualTo(voucherExpected1);
    }

    @Test
    @DisplayName("바우처 ID를 가지고 바우처를 가져온다.")
    void findById() {
        // given
        UUID voucherId1 = UUID.randomUUID();

        Voucher voucherExpected1 = new FixedAmountVoucher(voucherId1, 3000);

        // when
        voucherRepository.save(voucherExpected1);
        Voucher voucherActual1 = voucherRepository.findById(voucherId1);

        // then
        assertThat(voucherActual1).usingRecursiveComparison().isEqualTo(voucherExpected1);
    }

    @Test
    @DisplayName("저장된 모든 바우처를 조회한다.")
    void findAll() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucherExpected1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucherExpected2 = new PercentDiscountVoucher(voucherId2, 30);

        List<Voucher> voucherListExpected = List.of(voucherExpected1, voucherExpected2);

        // when
        voucherRepository.save(voucherExpected1);
        voucherRepository.save(voucherExpected2);

        List<Voucher> voucherList = voucherRepository.findAll();

        // then
        assertThat(voucherList).usingRecursiveFieldByFieldElementComparator().isEqualTo(voucherListExpected);
    }

    @Test
    @DisplayName("바우처 ID를 가지고 바우처를 삭제한다.")
    void deleteById() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 3000);

        // when
        voucherRepository.save(voucher);
        voucherRepository.deleteById(voucherId);

        // then
        assertThatThrownBy(() -> voucherRepository.findById(voucherId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("바우처를 모두 삭제한다.")
    void deleteAll() {
        // given
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher voucherExpected1 = new FixedAmountVoucher(voucherId1, 3000);
        Voucher voucherExpected2 = new PercentDiscountVoucher(voucherId2, 30);

        // when
        voucherRepository.save(voucherExpected1);
        voucherRepository.save(voucherExpected2);
        voucherRepository.deleteAll();

        // then
        assertThat(voucherRepository.findAll().size()).isEqualTo(0);
    }
}
