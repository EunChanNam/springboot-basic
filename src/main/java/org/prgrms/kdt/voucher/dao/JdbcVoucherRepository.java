package org.prgrms.kdt.voucher.dao;

import org.prgrms.kdt.exception.NotUpdateException;
import org.prgrms.kdt.voucher.domain.DiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Profile({"default", "test", "web"})
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = UUID.fromString(resultSet.getString("id"));
        VoucherType voucherType = VoucherType.getTypeByStr(resultSet.getString("type"));
        DiscountPolicy discountPolicy = voucherType.createPolicy(resultSet.getDouble("amount"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Voucher(voucherId, voucherType, discountPolicy, createdAt);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select id, type, amount, created_at from voucher WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    voucherRowMapper,
                    voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO voucher(id, type, amount, created_at) VALUES (?, ?, ?, ?)";
        int update = jdbcTemplate.update(sql,
                voucher.getVoucherId().toString(),
                voucher.getVoucherType().getDescripton(),
                voucher.getDiscountPolicy().getAmount(),
                Timestamp.valueOf(voucher.getCreatedAt()));
        if (update != 1) {
            throw new NotUpdateException("insert가 제대로 이루어지지 않았습니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select id, type, amount, created_at from voucher", voucherRowMapper);
    }
}
