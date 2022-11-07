package org.prgrms.springorder.controller;

import java.util.stream.Collectors;
import org.prgrms.springorder.request.VoucherCreateRequest;
import org.prgrms.springorder.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineController {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);

    private final VoucherService voucherService;

    private final ConsoleInput consoleInput;

    private final ConsoleOutput consoleOutput;

    public CommandLineController(VoucherService voucherService,
        ConsoleInput consoleinput,
        ConsoleOutput consoleOutput) {
        this.voucherService = voucherService;
        this.consoleInput = consoleinput;
        this.consoleOutput = consoleOutput;
    }

    public void run() {

        while (ConsoleRunningStatus.isRunning()) {
            try {
                displayCommandGuide();

                String inputString = consoleInput.inputString();

                Command command = Command.of(inputString);

                execute(command);
            } catch (RuntimeException e) {
                logger.warn("errorName : {}, errorMessage : {}", e.getClass().getName(),
                    e.getMessage());
                consoleOutput.showMessage(e.getMessage());
            }
        }
    }

    public void execute(Command command) {
        switch (command) {
            case CREATE -> {
                VoucherCreateRequest voucherCreateRequest = consoleInput.getVoucherCreateRequest(
                    consoleOutput);

                consoleOutput.showMessage(
                    "created Voucher. : " + voucherService.createVoucher(voucherCreateRequest));
            }

            case LIST -> consoleOutput.showMessages(voucherService.findAll().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));

            case EXIT -> ConsoleRunningStatus.stop();
        }
    }

    private void displayCommandGuide() {
        consoleOutput.showMessage("=== Voucher Program ===",
            "Type 'exit' to exit the program. ",
            "Type 'create' to create a new voucher.",
            "Type 'list' to list all vouchers. "
        );
    }

}
