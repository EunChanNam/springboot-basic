package org.prgrms.springbootbasic.repository.customer;

import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) values(UUID_TO_BIN(?), ?, ?)";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String UPDATE_BY_ID_SQL = "update customers set name = ? where customer_id = UUID_TO_BIN(?)";
    private final String SELECT_BY_EMAIL = "select * from customers where email = ?";

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/springboot_basic",
                "hyuk", "hyuk1234!");
            var statement = connection.prepareStatement(SELECT_ALL_SQL);
            var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var customerId = toUUID(resultSet.getBytes("customer_id"));
                var name = resultSet.getString("name");
                var email = resultSet.getString("email");
                customers.add(new Customer(customerId, name, email));
            }
        } catch (SQLException throwables) {
            logger.error("error in findAll()", throwables);
        }
        return customers;
    }

    public UUID save(Customer customer) {
        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/springboot_basic",
                "hyuk", "hyuk1234!");
            var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("error in save()", throwables);
        }
        return customer.getCustomerId();
    }

    public void removeAll() {
        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/springboot_basic",
                "hyuk", "hyuk1234!");
            var statement = connection.prepareStatement(DELETE_ALL_SQL)
        ) {
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("error in removeAll()", throwables);
        }
    }

    public void changeName(UUID customerId, String name) {
        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/springboot_basic",
                "hyuk", "hyuk1234!");
            var statement = connection.prepareStatement(UPDATE_BY_ID_SQL)
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("error in removeAll()", throwables);
        }
    }

    public List<Customer> findByEmail(String email) {
        List<Customer> customers = new ArrayList<>();

        try (
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/springboot_basic",
                "hyuk", "hyuk1234!");
            var statement = connection.prepareStatement(SELECT_BY_EMAIL);
        ) {
            statement.setString(1, email);
            try (
                var resultSet = statement.executeQuery()
            ) {
                while (resultSet.next()) {
                    var customerId = toUUID(resultSet.getBytes("customer_id"));
                    var name = resultSet.getString("name");
                    var customerEmail = resultSet.getString("email");
                    customers.add(new Customer(customerId, name, customerEmail));
                }
            }
        } catch (SQLException throwables) {
            logger.error("Exception in findByEmail()", throwables);
        }
        return customers;
    }
}
