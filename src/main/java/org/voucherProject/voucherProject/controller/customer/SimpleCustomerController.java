package org.voucherProject.voucherProject.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.entity.customer.Customer;
import org.voucherProject.voucherProject.entity.customer.CustomerDto;
import org.voucherProject.voucherProject.service.customer.CustomerService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SimpleCustomerController implements CustomerController {

    private final CustomerService customerService;

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        return customerService.save(new Customer(customerDto.getCustomerId(), customerDto.getCustomerName(), customerDto.getCustomerEmail(), customerDto.getPassword()));
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerService.findAll()
                .stream()
                .map(c -> CustomerDto.builder()
                        .customerName(c.getCustomerName())
                        .customerEmail(c.getCustomerEmail())
                        .build()
                ).toList();
    }

    @Override
    public CustomerDto findById(CustomerDto customerDto) {
        Customer customer = customerService.findById(customerDto.getCustomerId());
        return CustomerDto.builder()
                .customerName(customer.getCustomerName())
                .customerEmail(customerDto.getCustomerEmail())
                .build();
    }
}
