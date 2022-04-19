package com.prgrms.management.command.service;

import com.prgrms.management.command.domain.Command;
import com.prgrms.management.command.domain.GuideType;
import com.prgrms.management.command.io.Input;
import com.prgrms.management.command.io.Output;
import com.prgrms.management.customer.service.CustomerService;
import com.prgrms.management.voucher.domain.VoucherType;
import com.prgrms.management.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandService {
    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandService(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run() {
        while (true) {
            output.printGuide(GuideType.COMMAND.getMESSAGE());
            try {
                String inputCommand = input.readLine();
                Command command = Command.of(inputCommand);
                execute(command);
            } catch (RuntimeException e) {
                logger.warn("{}:{}",e.getClass(),e.getMessage());
            }
        }
    }

    private void execute(Command command) {
        switch (command) {
            case CREATE:
                output.printGuide(GuideType.VOUCHER.getMESSAGE());
                String inputVoucherType = input.readLine();
                output.printGuide(GuideType.DISCOUNT.getMESSAGE());
                String inputAmount = input.readLine();
                voucherService.createVoucher(inputVoucherType, inputAmount);
                break;
            case LIST:
                output.printList(voucherService.findAll());
                break;
            case BLACKLIST:
                output.printList(customerService.findBlackList());
                break;
            case EXIT:
                System.exit(0);
        }
    }
}
