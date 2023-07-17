package com.example.voucher.repository;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.PercentDiscountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.repository.voucher.JdbcVoucherRepository;

@JdbcTest
@ContextConfiguration(classes = JdbcVoucherRepository.class)
class JdbcVoucherRepositoryTest {

    public static String FAILED = "test failed";

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @DisplayName("바우처 객체가 있을 때 저장하면 디비에서 동일한 ID의 데이터를 객체로 반환한다.")
    @Test
    void save() {
        Voucher expectedVoucher = new FixedAmountVoucher(10L);

        Voucher actualVoucher = jdbcVoucherRepository.save(expectedVoucher);

        assertEquals(expectedVoucher, actualVoucher);
    }

    @DisplayName("데이터가 디비에 저장 되어있을때 존재하는 ID를 통해 조회하면 동일한 ID의 데이터를 반환한다.")
    @Test
    void findById() {
        Voucher expectedVoucher = jdbcVoucherRepository.save(new PercentDiscountVoucher(50L));
        UUID id = expectedVoucher.getVoucherId();

        Voucher actualVoucher = jdbcVoucherRepository.findById(id);

        assertEquals(expectedVoucher, actualVoucher);
    }

    @DisplayName("ID에 대응하는 데이터가 디비에 저장되어 있지 않을 떄 ID를 통해 조회하면 EmptyResultDataAccessException 예외가 발생한다")
    @Test()
    void findByWrongId() {
        UUID id = UUID.randomUUID();

        assertThrows(EmptyResultDataAccessException.class, () -> jdbcVoucherRepository.findById(id));
    }

    @DisplayName("9개의 데이터가 디비에 저장되어 있을 때 전체 초회하면 9개의 데이터가 조회된다")
    @Test()
    void findAll() {
        List<Voucher> vouchers = LongStream.iterate(10, n -> n + 10)
            .limit(9)
            .mapToObj(v -> new FixedAmountVoucher(v))
            .collect(Collectors.toList());
        Integer expectedSize = vouchers.size();

        vouchers.stream().forEach(v -> jdbcVoucherRepository.save(v));

        List<Voucher> selectedVouchers = jdbcVoucherRepository.findAll();
        Integer actualSize = selectedVouchers.size();
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("데이터가 디비에 저장되어 있지 않을 때 전체 조회하면 0개의 데이터가 조회된다")
    @Test
    void findAllEmpty() {
        Integer expectedSize = 0;

        List<Voucher> selectedVouchers = jdbcVoucherRepository.findAll();
        Integer actualSize = selectedVouchers.size();

        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("2개의 데이터가 디비에 저장되어 있을 때 전체 삭제하면 0개의 데이터가 조회된다.")
    @Test
    void deleteAll() {
        Integer expectedSize = 0;
        jdbcVoucherRepository.save(new FixedAmountVoucher(10L));
        jdbcVoucherRepository.save(new FixedAmountVoucher(20L));

        jdbcVoucherRepository.deleteAll();

        List<Voucher> selectedVouchers = jdbcVoucherRepository.findAll();
        Integer actualSize = selectedVouchers.size();
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("등록된 데이터의 ID가 있을 때 ID를 통해 삭제하면 해당 ID로 다시 조회하면 EmptyResultDataAccessException 예외가 발생한다")
    @Test
    void deleteById() {
        Voucher voucher = jdbcVoucherRepository.save(new FixedAmountVoucher(10L));

        jdbcVoucherRepository.deleteById(voucher.getVoucherId());

        assertThrows(EmptyResultDataAccessException.class,
            () -> jdbcVoucherRepository.findById(voucher.getVoucherId()));
    }

    @DisplayName("동일한 아이디의 바우처 타입과 할인정보를 변경할 떄 update 명령을 수행하면 해당 데이턴의 정보가 저당된다.")
    @Test
    void update() {
        Voucher savedVoucher = jdbcVoucherRepository.save(new FixedAmountVoucher(10L));
        Voucher actualVoucher = VoucherType.PERCENT_DISCOUNT.createVoucher(savedVoucher.getVoucherId(), 50L);

        Voucher expectedVoucher = jdbcVoucherRepository.update(actualVoucher);

        assertEquals(expectedVoucher, actualVoucher);
    }

}
