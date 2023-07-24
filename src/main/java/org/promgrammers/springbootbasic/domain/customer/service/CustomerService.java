package org.promgrammers.springbootbasic.domain.customer.service;

import org.promgrammers.springbootbasic.domain.customer.dto.request.CreateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.request.UpdateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.CustomerRepository;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.promgrammers.springbootbasic.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.DUPLICATED_USERNAME;
import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.NOT_FOUND_CUSTOMER;
import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.NOT_FOUND_VOUCHER;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public CustomerService(JdbcCustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        duplicatedUsername(customerRequest.username());

        Customer customer = new Customer(UUID.randomUUID(), customerRequest.username());
        customerRepository.save(customer);
        return new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        return new CustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        return new CustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findByVoucherId(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_VOUCHER));

        Customer customer = customerRepository.findByVoucherId(voucher.getVoucherId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        return new CustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomersResponse findAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        if (customerList == null || customerList.isEmpty()) {
            throw new BusinessException(NOT_FOUND_CUSTOMER);
        }

        List<CustomerResponse> customerResponseList = customerRepository.findAll()
                .stream()
                .map(CustomerResponse::new)
                .toList();

        return new CustomersResponse(customerResponseList);
    }

    @Transactional
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    @Transactional
    public CustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findById(updateCustomerRequest.customerId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        duplicatedUsername(updateCustomerRequest.username());

        customer.updateUsername(updateCustomerRequest.username());
        customer.updateCustomerType(updateCustomerRequest.customerType());
        customerRepository.update(customer);

        CustomerResponse customerResponse = new CustomerResponse(customer);
        return customerResponse;
    }

    @Transactional
    public void deleteById(UUID customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        customerRepository.deleteById(customerId);
    }

    private void duplicatedUsername(String username) {

        customerRepository.findByUsername(username).ifPresent(customer -> {
            throw new BusinessException(DUPLICATED_USERNAME);
        });
    }
}
