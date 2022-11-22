package org.prgrms.springbootbasic.processor;

import org.prgrms.springbootbasic.CustomerInputDto;
import org.prgrms.springbootbasic.NotificationProperties;
import org.prgrms.springbootbasic.dto.Response;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.type.MethodType;
import org.prgrms.springbootbasic.util.CommandLineInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.prgrms.springbootbasic.type.MethodType.*;
import static org.prgrms.springbootbasic.util.UUIDUtil.isUUID;


@Component
public class CustomerProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(VoucherProcessor.class);
    private final NotificationProperties notificationProperties;
    private final CustomerService customerService;

    @Autowired
    public CustomerProcessor(NotificationProperties notificationProperties, CustomerService customerService) {
        this.notificationProperties = notificationProperties;
        this.customerService = customerService;
    }

    @Override
    public Response process() {
        String method = CommandLineInput.getInput(notificationProperties.getCustomerPrompt());
        while (!MethodType.validate(method)) {
            System.out.println(notificationProperties.getWrongInput());
            method = CommandLineInput.getInput(notificationProperties.getVoucherPrompt());
        }

        List<Customer> customerList = new ArrayList<>();
        if (isCreate(method)) {
            customerList.add(customerProcess());
        } else if (isLookupList(method)) {
            customerList.addAll(customerService.lookupCustomerList());
        } else if (isUpdate(method)) {
            customerList.add(updateProcess());
        } else if (isDelete(method)) {
            customerList.add(deleteProcess());
        }

        return new Response(MethodType.valueOf(method.toUpperCase()), customerList);
    }

    private Customer customerProcess() {
        String name = CommandLineInput.getInput("name :");
        String email = CommandLineInput.getInput("email :");
        CustomerInputDto customerInputDto = new CustomerInputDto(name, email);

        return customerService.createCustomer(customerInputDto);
    }

    private Customer updateProcess() {
        String customerId;
        Customer targetCustomer;
        do {
            customerId = getCustomerId();
            targetCustomer = customerService.findCustomerById(customerId);
        } while (targetCustomer == null);

        String name = CommandLineInput.getInput("name : ");
        targetCustomer.changeName(name);
        customerService.updateCustomer(targetCustomer);

        return targetCustomer;
    }

    private Customer deleteProcess() {
        String customerId;
        Customer targetCustomer;
        do {
            customerId = getCustomerId();
            targetCustomer = customerService.findCustomerById(customerId);
        } while (targetCustomer == null);
        customerService.deleteCustomerById(targetCustomer);

        return targetCustomer;
    }

    private String getCustomerId() {
        String customerId;
        do {
            customerId = CommandLineInput.getInput("what is customerId to change? : ");
        } while (!isUUID(customerId));

        return customerId;
    }
}
