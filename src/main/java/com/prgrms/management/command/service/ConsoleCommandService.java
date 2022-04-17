package com.prgrms.management.command.service;

import com.prgrms.management.command.domain.Command;
import com.prgrms.management.config.GuideType;
import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import com.prgrms.management.customer.service.CustomerService;
import com.prgrms.management.voucher.domain.VoucherRequest;
import com.prgrms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleCommandService implements CommandService {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleCommandService.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public ConsoleCommandService(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        while (true) {
            output.printGuide(GuideType.COMMAND.getMESSAGE());
            try {
                String inputCommand = input.readLine();
                Command command = Command.of(inputCommand);
                execute(command);
            } catch (RuntimeException e) {
                logger.info("{}:{}",e.getClass(),e.getMessage());
            }
        }
    }

    @Override
    public void execute(Command command) {
        switch (command) {
            case CREATE:
                VoucherRequest voucherRequest = input.inputVoucherType();
                voucherService.createVoucher(voucherRequest);
                break;
            case LIST:
                output.printList(voucherService.findAll());
                break;
            case CREATECUSTOMER:
                customerService.createCustomer();
                break;
            case DELETECUSTOMER:
                customerService.deleteCustomer();
                break;
            case LISTCUSTOMER:
                customerService.findCustomers();
                break;
            case CREATEVOUCHER:
                customerService.createVoucher();
                break;
            case DELETEVOUCHER:
                customerService.deleteVoucher();
                break;
            case LISTVOUCHER:
                customerService.findCustomersByVoucherType();
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
