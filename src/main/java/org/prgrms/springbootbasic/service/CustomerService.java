package org.prgrms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbootbasic.dto.CustomerInputDto;
import org.prgrms.springbootbasic.dto.CustomerUpdateDto;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.exception.CustomerNotFoundException;
import org.prgrms.springbootbasic.exception.DuplicatedEmailException;
import org.prgrms.springbootbasic.mapper.CustomerDtoMapper;
import org.prgrms.springbootbasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerInputDto customerInputDto) {
        try {
            return customerRepository.insert(
                    CustomerDtoMapper.inputDtoToCustomer(customerInputDto));
        } catch (DuplicateKeyException e) {
            throw new DuplicatedEmailException();
        }
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer editCustomer(CustomerUpdateDto customerUpdateDto) {
        Customer targetCustomer = customerRepository.findById(
                        UUID.fromString(customerUpdateDto.getCustomerId()))
                .orElseThrow(CustomerNotFoundException::new);
        targetCustomer.changeName(customerUpdateDto.getName());
        return customerRepository.update(targetCustomer).get();
    }

    public int removeCustomerById(String customerId) {
        customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(CustomerNotFoundException::new);
        return customerRepository.deleteById(UUID.fromString(customerId));
    }

    public boolean isDuplicatedEmail(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }
}
