package com.programmers.voucher;

import com.programmers.voucher.menu.Menu;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.View;
import com.programmers.voucher.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.programmers.voucher.menu.Menu.EXIT;
import static com.programmers.voucher.menu.Menu.findMenu;
import static com.programmers.voucher.menu.Message.*;

@Component
public class CommandLineApplication implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final View view;
    private final VoucherService voucherService;

    @Autowired
    public CommandLineApplication(View view, VoucherService voucherService) {
        this.view = view;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        String userCommand = "";

        while (!userCommand.equals(EXIT.getMenu())) {

            view.printMessage(GREETING_MESSAGE);
            userCommand = view.getUserCommand().toUpperCase();

            try {

                Menu userMenu = findMenu(userCommand);
                executeUserCommand(userMenu);

            } catch (Exception e) {
                logger.error("사용자 커맨드 입력값 오류", e);
                view.printMessage(e.getMessage());
            }
        }
    }

    private void executeUserCommand(Menu userMenu) {
        switch (userMenu) {
            case CREATE:
                view.printMessage(VOUCHER_TYPE_MESSAGE);
                String voucherTypeInput = view.getUserCommand();

                view.printMessage(VOUCHER_VALUE_MESSAGE);
                String value = view.getUserCommand();

                voucherService.register(voucherTypeInput, value);
                view.printMessage(VOUCHER_CREATE_SUCCESS);
                break;

            case LIST:
                showVoucherList();
                break;

            case EXIT:
                return;
        }
    }

    private void showVoucherList() {
        List<Voucher> vouchers = voucherService.findAll();
        for (Voucher voucher : vouchers) {
            view.printVoucher(voucher);
        }
    }
}
