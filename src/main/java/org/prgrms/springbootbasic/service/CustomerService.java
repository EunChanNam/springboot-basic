package org.prgrms.springbootbasic.service;

import org.prgrms.springbootbasic.CustomerInputDto;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerInputDto customerInputDto) {
        return customerRepository.insert(customerInputDto.toCustomer());
    }

    public List<Customer> lookupCustomerList() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(String customerId) {
        return customerRepository.findById(UUID.fromString(customerId));
    }

    public Optional<Customer> updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    public int deleteCustomerById(Customer customer) {
        return customerRepository.deleteById(customer.getCustomerId());
    }
}
