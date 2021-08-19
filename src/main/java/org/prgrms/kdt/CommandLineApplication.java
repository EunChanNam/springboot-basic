package org.prgrms.kdt;

import org.prgrms.kdt.core.*;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.prgrms.kdt.service.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;
import java.util.regex.Pattern;

public class CommandLineApplication {
    private static final Input input = new Console();
    private static final Output output = new Console();

    enum Command {
        CREATE("create"),
        LIST("list"),
        EXIT("exit"),
        FIXED("fixed"),
        PERCENT("percent"),
        INVALID("invalid");

        private static final Map<String, Command> nameIndex = new HashMap<>(Command.values().length);

        static {
            for (Command cmd : Command.values()) {
                nameIndex.put(cmd.getValue(), cmd);
            }
        }

        private final String value;

        Command(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Command lookup(String value) {
            return nameIndex.getOrDefault(value, INVALID);
        }
    }

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        var prompt = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""";

        String inputStr;
        while (true) {
            inputStr = input.input(prompt);
            switch (Command.lookup(inputStr)) {
                case EXIT -> {
                    exit();
                    applicationContext.close();
                    return;
                }
                case CREATE -> {
                    if (createVoucher(voucherService)) {
                        output.printMessage("create success!");
                    }
                }
                case LIST -> listVoucher(voucherService);
                default -> output.inputError(inputStr);
            }
        }

    }

    private static void listVoucher(VoucherService voucherService) {
        var vouchers = voucherService.listVoucher();
        if (vouchers.isEmpty()) {
            output.printMessage("No Voucher Data");
        } else {
            output.printMessage("=== Voucher List ===");
            vouchers.forEach(v -> output.printMessage(v.toString()));
        }
    }

    private static boolean createVoucher(VoucherService voucherService) {
        String inputStr = input.input(
                "Creating a new voucher. Choose a voucher type (fixed or percent): "
        );
        var cmd = Command.lookup(inputStr);
        if (cmd == Command.INVALID) {
            output.inputError(inputStr);
            return false;
        }
        inputStr = input.input("How much discount?: ");
        if (!isDigit(inputStr)) {
            output.inputError(inputStr);
            return false;
        }

        var value = Long.parseLong(inputStr);
        Optional<Voucher> createdVoucher = Optional.empty();
        if (cmd == Command.FIXED) {
            createdVoucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT, value);
        } else if (cmd == Command.PERCENT) {
            createdVoucher = voucherService.createVoucher(VoucherType.PERCENT, value);
        }

        if(createdVoucher.isPresent()) {
            output.printMessage("Creation Success: " + createdVoucher);
            return true;
        } else {
            output.printMessage("Creation fail");
            return false;
        }

    }

    private static boolean isDigit(String input) {
        return Pattern.matches("^[0-9]*$", input);
    }

    private static void exit() {
        output.printMessage("BYE!");
    }
}
