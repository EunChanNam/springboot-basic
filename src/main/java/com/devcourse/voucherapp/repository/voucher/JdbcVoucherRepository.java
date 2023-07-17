package com.devcourse.voucherapp.repository.voucher;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.entity.voucher.VoucherType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher(id, type, discount_amount) values (:id, :typeOption, :discountAmount)";
        template.update(sql, getParameterSource(voucher));

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        String sql = "select id, type, discount_amount from voucher";

        return template.query(sql, getVoucherRowMapper());
    }

    @Override
    public Optional<Voucher> findVoucherById(String id) {
        String sql = "select id, type, discount_amount from voucher where id = :id";

        try {
            Voucher voucher = template.queryForObject(sql, getParameterMap(id), getVoucherRowMapper());

            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        String sql = "update voucher set type = :typeOption, discount_amount = :discountAmount where id = :id";
        template.update(sql, getParameterSource(voucher));

        return voucher;
    }

    @Override
    public int deleteById(String id) {
        String sql = "delete from voucher where id = :id";

        return template.update(sql, getParameterMap(id));
    }

    private SqlParameterSource getParameterSource(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("typeOption", voucher.getType().getOption())
                .addValue("discountAmount", voucher.getDiscountAmount());
    }

    private Map<String, String> getParameterMap(String id) {
        return Map.of("id", id);
    }

    private RowMapper<Voucher> getVoucherRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            String typeOption = resultSet.getString("type");
            int discountAmount = resultSet.getInt("discount_amount");

            return VoucherType.from(typeOption)
                    .makeVoucher(UUID.fromString(id), String.valueOf(discountAmount));
        };
    }
}
