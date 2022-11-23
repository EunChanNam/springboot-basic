package com.example.springbootbasic.controller.customer;

import com.example.springbootbasic.controller.request.CreateCustomerRequest;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.dto.customer.CustomerDto;
import com.example.springbootbasic.service.customer.JdbcCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewCustomerController {

    private final JdbcCustomerService customerService;

    public ViewCustomerController(JdbcCustomerService jdbcCustomerService) {
        this.customerService = jdbcCustomerService;
    }

    @GetMapping("/v1/customers")
    public String customerList(Model model) {
        List<CustomerDto> result = customerService.findAllCustomers()
                .stream()
                .map(CustomerDto::newInstance)
                .toList();
        model.addAttribute("customers", result);
        return "customer-list";
    }

    @GetMapping("/v1/customer-add")
    public String customerAddForm() {
        return "customer-add";
    }

    @PostMapping("/v1/customer-add")
    public String customerAddForm(CreateCustomerRequest request) {
        CustomerStatus customerStatus = CustomerStatus.of(request.status());
        customerService.saveCustomer(new Customer(customerStatus));
        return "redirect:customers";
    }

    @GetMapping("/v1/customer-vouchers/{customerId}")
    public String customerVoucherList(@PathVariable Long customerId, Model model) {
        Customer findCustomer = customerService.findCustomerById(customerId);
        List<Voucher> findVouchers = customerService.findVouchersByCustomerId(customerId);
        findVouchers.forEach(findCustomer::receiveFrom);
        CustomerDto result = CustomerDto.newInstance(findCustomer);
        model.addAttribute("customer", result);
        return "customer-voucher-list";
    }

    @GetMapping("/v1/customer-vouchers")
    public String findCustomerDetailPage(@RequestParam Long customerId) {
        return  "redirect:customer-vouchers/" + customerId;
    }

    @GetMapping("/v1/customers/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:";
    }
}
