package com.prgms.VoucherApp.domain.voucher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class VoucherJdbcDao implements VoucherDao {

    private static Logger logger = LoggerFactory.getLogger(VoucherJdbcDao.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VoucherJdbcDao(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Voucher voucher) {
        String sql = "INSERT INTO voucher VALUES (:id, :amount, :type)";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", voucher.getVoucherId().toString())
            .addValue("amount", voucher.getAmount())
            .addValue("type", voucher.getVoucherType().getVoucherTypeName());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM voucher";

        List<Voucher> vouchers = namedParameterJdbcTemplate.query(sql, voucherRowMapper());
        return vouchers;
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM voucher where id = :id";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", voucherId);
        try {
            Voucher voucher = namedParameterJdbcTemplate.queryForObject(sql, paramMap, voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType type) {
        String sql = "SELECT * FROM voucher where type = :type";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("type", type.getVoucherTypeName());

        List<Voucher> vouchers = namedParameterJdbcTemplate.query(sql, paramMap, voucherRowMapper());
        return vouchers;
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "UPDATE voucher SET amount = :amount, type = :type WHERE id = :id";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("amount", voucher.getAmount())
            .addValue("type", voucher.getVoucherType().getVoucherTypeName())
            .addValue("id", voucher.getVoucherId().toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 를 입력 받았습니다.");
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "DELETE FROM voucher WHERE id = :id";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", voucherId.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 를 입력받았습니다.");
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            String type = resultSet.getString("type");
            VoucherType voucherType = VoucherType.findByVoucherTypeName(type);
            if (voucherType.isFixedVoucher()) {
                return new FixedAmountVoucher(UUID.fromString(id), amount);
            }

            return new PercentDiscountVoucher(UUID.fromString(id), amount);
        };
    }

}
