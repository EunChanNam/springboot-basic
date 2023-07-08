package org.weekly.weekly.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.dto.response.CustomerDto;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.customer.repository.CustomerRepository;
import org.weekly.weekly.util.ExceptionMsg;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerDto createCustomer(CustomerCreationRequest creationRequest) {
        validateCustomerNotExist(creationRequest.getEmail());

        Customer customer = creationRequest.toCustomer();
        customerRepository.insert(customer);
        return CustomerDto.of(customer);
    }

    @Transactional
    public void deleteCustomer(CustomerUpdateRequest updateRequest) {
        String email = updateRequest.email();
        customerRepository.deleteByEmail(email);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }


    public CustomerDto findDetailCustomer(CustomerUpdateRequest updateRequest) {
        String email = updateRequest.email();
        Customer customer = validateCustomerExistAndGet(email);
        return CustomerDto.of(customer);
    }

    public List<CustomerDto> findAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerDto::of).toList();
    }

    @Transactional
    public CustomerDto updateCustomer(CustomerUpdateRequest updateRequest) {
        validateCustomerNotExist(updateRequest.newEmail());

        Customer customer = validateCustomerExistAndGet(updateRequest.email());

        customer.updateName(updateRequest.newEmail());
        Customer updateCustomer = customerRepository.update(customer);
        return CustomerDto.of(updateCustomer);
    }

    private void validateCustomerNotExist(String email) {
        Optional<Customer> findCustomer = customerRepository.findByEmail(email);
        if (findCustomer.isPresent()) {
            throw new CustomerException(ExceptionMsg.SQL_EXIST);
        }
    }

    private Customer validateCustomerExistAndGet(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isEmpty()) {
            throw new CustomerException(ExceptionMsg.SQL_ERROR);
        }
        return customer.get();
    }
}
