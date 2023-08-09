package prgms.spring_week1.domain.customer.repository;

import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.customer.model.Customer;

import java.util.List;

@Repository
public interface CustomerRepository {
    void insert(Customer customer);

    List<Customer> findAll();

    Customer findByEmail(String email);

    void deleteByEmail(String email);

    void deleteAll();

    void updateInfo(String beforeUpdateEmail, String afterUpdateEmailInfo);
}
