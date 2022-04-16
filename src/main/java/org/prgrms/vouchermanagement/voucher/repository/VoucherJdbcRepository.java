package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

  private static final Logger log = LoggerFactory.getLogger(VoucherJdbcRepository.class);

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static RowMapper<Voucher> voucherRowMapper =  (resultSet, i) -> {
    UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
    long reduction = resultSet.getLong("reduction");
    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
    VoucherType voucherType = VoucherType.fromDbValue(resultSet.getInt("voucher_type"));
    log.info("voucher id -> {}, reduction -> {}, createdAt -> {}, voucher type -> {}", voucherId, reduction, createdAt, voucherType);
    return VoucherFactory.createVoucher(voucherId, reduction, createdAt, voucherType);
  };


  @Override
  public Voucher insert(Voucher voucher) {
    var update = jdbcTemplate.update(
      "insert into vouchers(voucher_id, reduction, created_at, voucher_type) values (uuid_to_bin(:voucherId), :reduction, :createdAt, :voucherType)",
      toParamMap(voucher));
    if(update != 1) {
      throw new RuntimeException("Nothing was inserted");
    }
    return voucher;
  }

  @Override
  public List<Voucher> findAll() {
    return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
  }

  @Override
  public List<Voucher> findByVoucherType(VoucherType voucherType) {
    Map<String, Object> paramMap = new HashMap<>() {{
      put("voucherType", voucherType.toDbValue());
    }};
    return jdbcTemplate.query("select * from vouchers where voucher_type = :voucherType ", paramMap, voucherRowMapper);
  }

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    return Optional.empty();
  }

  @Override
  public int count() {
    return jdbcTemplate.queryForObject("select count(*) from vouchers", Collections.emptyMap(), Integer.class);
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.update("delete from vouchers", Collections.emptyMap());
  }

  @Override
  public void deleteById(UUID voucherId) {

  }

  @Override
  public void updateById(UUID voucherId) {

  }

  private Map<String, Object> toParamMap(Voucher voucher) {
    return new HashMap<String, Object>(){{
      put("voucherId", voucher.getVoucherID().toString().getBytes());
      put("reduction", voucher.getReduction());
      put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
      put("voucherType", VoucherType.toDbValue(voucher));
    }};
  }

  static UUID toUUID(byte[] bytes) throws SQLException {
    var byteBuffer = ByteBuffer.wrap(bytes);
    return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
  }
}
