package prgms.spring_week1.domain.voucher.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import javax.sql.DataSource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@JdbcTest
class JdbcVoucherRepositoryTest {
    private VoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        jdbcVoucherRepository = new JdbcVoucherRepository(dataSource);
    }

    @Test
    void findAll_before() {
        assertThat(jdbcVoucherRepository.findAll(),hasSize(2));
    }

    @Test
    void findByType() {
        List<Voucher> fixedVoucherList = jdbcVoucherRepository.findByType("FIXED");
        assertThat(fixedVoucherList,hasSize(1));
        List<Voucher> percentVoucherList = jdbcVoucherRepository.findByType("PERCENT");
        assertThat(percentVoucherList,hasSize(1));
    }

    @Test
    void delete() {
        jdbcVoucherRepository.delete();
        assertThat(jdbcVoucherRepository.findAll(),hasSize(0));
    }
}
