package com.programmers.springbootbasic.voucher.repository;

import com.programmers.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.programmers.springbootbasic.voucher.domain.PercentDiscountVoucher;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcVoucherRepository.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @DisplayName("바우처를 저장한다")
    @Test
    void save() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);

        //when
        Voucher result = jdbcVoucherRepository.save(fixedAmountVoucher);

        //then
        assertThat(result.getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
    }

    @DisplayName("저장된 바우처들을 모두 조회한다")
    @Test
    void findAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);

        //when
        List<Voucher> result = jdbcVoucherRepository.findAll();

        //then
        assertThat(result.size(), is(2));
    }

    @DisplayName("바우처를 id로 조회한다")
    @Test
    void findById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Voucher> result = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(result.get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
    }

    @DisplayName("바우처를 id로 조회했을 때 존재하지 않으면 예외처리한다")
    @Test
    void findByIdException() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);

        //when
        //then
        assertThatThrownBy(() -> jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId()))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("바우처를 수정한다")
    @Test
    void update() {
        //given
        UUID id = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, "testName", 10L);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        //when
        Voucher result = jdbcVoucherRepository.update(new FixedAmountVoucher(id, "update", 20L));

        //then
        assertThat(result.getVoucherId(), is(id));
        assertThat(result.getVoucherName(), is("update"));
        assertThat(result.getVoucherValue(), is(20L));
    }

    @DisplayName("id로 바우처를 삭제한다")
    @Test
    void deleteById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        //when
        jdbcVoucherRepository.deleteById(fixedAmountVoucher.getVoucherId());
        List<Voucher> result = jdbcVoucherRepository.findAll();

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("저장된 모든 바우처들을 삭제한다")
    @Test
    void deleteAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);

        //when
        jdbcVoucherRepository.deleteAll();
        List<Voucher> result = jdbcVoucherRepository.findAll();

        //then
        assertThat(result).isEmpty();
    }
}