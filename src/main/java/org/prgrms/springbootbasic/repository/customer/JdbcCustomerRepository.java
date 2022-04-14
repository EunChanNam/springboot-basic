package org.prgrms.springbootbasic.repository.customer;

import static org.prgrms.springbootbasic.repository.DBErrorMsg.GOT_EMPTY_RESULT_MSG;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.NOTHING_WAS_INSERTED_EXP_MSG;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.COLUMN_CUSTOMER_ID;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.COLUMN_EMAIL;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.COLUMN_NAME;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.DELETE_ALL_SQL;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.INSERT_SQL;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.SELECT_ALL_SQL;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.SELECT_BY_EMAIL;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.SELECT_BY_ID;
import static org.prgrms.springbootbasic.repository.customer.CustomerDBConstString.UPDATE_BY_ID_SQL;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public List<Customer> findAll() {
        logger.info("findAll() called");

        return jdbcTemplate.query(SELECT_ALL_SQL, (resultSet, i) -> {
            var customerId = toUUID(resultSet.getBytes(COLUMN_CUSTOMER_ID));
            var name = resultSet.getString(COLUMN_NAME);
            var email = resultSet.getString(COLUMN_EMAIL);
            return new Customer(customerId, name, email);
        });
    }

    @Override
    public UUID save(Customer customer) {
        logger.info("save() called");

        var insert = jdbcTemplate.update(INSERT_SQL,
            customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8),
            customer.getName(),
            customer.getEmail());
        if (insert != 1) {
            throw new RuntimeException(NOTHING_WAS_INSERTED_EXP_MSG);
        }
        return customer.getCustomerId();
    }

    @Override
    public void removeAll() {
        logger.info("removeAll() called");

        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    @Override
    public UUID changeName(Customer customer) {
        logger.info("changeName() called");

        var update = jdbcTemplate.update(UPDATE_BY_ID_SQL,
            customer.getName(),
            customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
        if (update != 1) {
            throw new RuntimeException(GOT_EMPTY_RESULT_MSG);
        }
        return customer.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        logger.info("findById() called");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, (resultSet, i) -> {
                var id = toUUID(resultSet.getBytes(COLUMN_CUSTOMER_ID));
                var name = resultSet.getString(COLUMN_NAME);
                var email = resultSet.getString(COLUMN_EMAIL);
                return new Customer(id, name, email);
            }, customerId.toString().getBytes(StandardCharsets.UTF_8)));
        } catch (EmptyResultDataAccessException e) {
            logger.info("findById() Got empty result");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        logger.info("findByEmail() called");

        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(SELECT_BY_EMAIL, (resultSet, i) -> {
                    var customerId = toUUID(resultSet.getBytes(COLUMN_CUSTOMER_ID));
                    var name = resultSet.getString(COLUMN_NAME);
                    var customerEmail = resultSet.getString(COLUMN_EMAIL);
                    return new Customer(customerId, name, customerEmail);
                }, email));
        } catch (EmptyResultDataAccessException e) {
            logger.info("findByEmail() Got empty result");
            return Optional.empty();
        }
    }
}
