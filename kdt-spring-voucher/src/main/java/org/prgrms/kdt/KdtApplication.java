package org.prgrms.kdt;

import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.input.MenuCommand;
import org.prgrms.kdt.input.UserInput;
import org.prgrms.kdt.input.VoucherCommand;
import org.prgrms.kdt.output.Output;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);
        Output outputConsole = applicationContext.getBean(Output.class);
        UserInput userInputMenu = applicationContext.getBean(UserInput.class);
        VoucherController voucherController = applicationContext.getBean(VoucherController.class);

        while (true) {
            outputConsole.displayMenu();
            outputConsole.displayUserInputLine();
            String userInputMenuCommand = userInputMenu.userInputMenuCommand();
            MenuCommand UserInputMenuCommand = MenuCommand.findByUserInputMenuCommand(userInputMenuCommand);
            switch (UserInputMenuCommand) {
                case EXIT -> {
                    return;
                }
                case CREATE -> {
                    outputConsole.displayVoucherCreateMenu();
                    outputConsole.displayUserInputLine();
                    String userInputVoucherCreateMenuCommand = userInputMenu.userInputVoucherCreateMenuCommand();
                    VoucherCommand UserInputVoucherCommand = VoucherCommand.findByUserInputVoucherCommand(userInputVoucherCreateMenuCommand);
                    try {
                        voucherController.createVoucher(UserInputVoucherCommand);
                    } catch (IllegalArgumentException e) {
                        switch (UserInputVoucherCommand) {
                            case FIXED_AMOUNT -> {
                                
                            }
                            case PERCENT_DISCOUNT -> {

                            }
                        }
                    }
                }
                case LIST -> outputConsole.displayAllVoucherList();
                case WRONG -> outputConsole.userInputWrongValue();
            }
        }
    }
}
