package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.entity.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private static final String INSERT = "INSERT INTO voucher(id, type, amount) VALUES(?, ?, ?)";
    private static final String FIND_ALL = "SELECT * FROM voucher";

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            jdbcTemplate.update(INSERT, voucher.getId().toString(), voucher.getType().name(), voucher.getAmount());
            return voucher;
        } catch (DataAccessException exception) {
            logger.error("error => {}", exception.getMessage());
            throw new RuntimeException("Failed to insert voucher");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    private final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> new Voucher(
            UUID.fromString(rs.getString("id")),
            VoucherType.valueOf(rs.getString("type")),
            rs.getInt("amount")
    );
}
