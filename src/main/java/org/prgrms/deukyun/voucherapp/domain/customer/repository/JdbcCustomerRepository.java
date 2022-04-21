package org.prgrms.deukyun.voucherapp.domain.customer.repository;

import org.prgrms.deukyun.voucherapp.domain.customer.entity.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

/**
 * Jdbc 고객 리포지토리
 */
//@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String insertQuery = "INSERT INTO customer(customer_id, customer_name, blacklist) VALUES (:id, :name, :blacklist)";
    private static final String findAllQuery = "SELECT * FROM customer";
    private static final String findByIdQuery = "SELECT * FROM customer WHERE customer_id = :id";
    private static final String clearQuery = "DELETE FROM customer";

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        Map<String, Object> paramMap = resolveParamMap(customer);

        jdbcTemplate.update(insertQuery, paramMap);
        return customer;
    }

    private Map<String, Object> resolveParamMap(Customer customer) {
        return Map.of("id", customer.getId(),
                "name", customer.getName(),
                "blacklist", customer.isBlackList());
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        try {
            Map<String, Object> paramMap = Collections.singletonMap("id", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdQuery, paramMap, customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(findAllQuery, customerRowMapper);
    }

    @Override
    public void clear() {
        jdbcTemplate.update(clearQuery, Collections.emptyMap());
    }

    private static final RowMapper<Customer> customerRowMapper = (rs, i) ->
        new Customer(UUID.fromString(rs.getString("customer_id")),
            rs.getString("customer_name"),
            rs.getBoolean("blackList"),
            Collections.emptyList());
}
