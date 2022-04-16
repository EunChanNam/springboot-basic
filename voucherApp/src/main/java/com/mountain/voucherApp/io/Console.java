package com.mountain.voucherApp.io;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.enums.Menu;
import com.mountain.voucherApp.voucher.VoucherEntity;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.mountain.voucherApp.constants.Color.*;
import static com.mountain.voucherApp.constants.CommonCharacter.INPUT_PROMPT;
import static com.mountain.voucherApp.constants.Message.*;
import static com.mountain.voucherApp.utils.MenuUtil.getMenuMap;

@Component
public class Console implements Input, Output {

    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    private void changeColor(String color) {
        textTerminal.getProperties().setPromptColor(color);
    }

    @Override
    public String input() {
        changeColor(INPUT_COLOR);
        String command = textIO.newStringInputReader()
                .read(INPUT_PROMPT);
        return command;
    }

    @Override
    public void close() {
        textTerminal.abort();
    }

    @Override
    public void printManual() {
        changeColor(MANUAL_COLOR);
        textTerminal.println(MANUAL_TITLE);
        Map<String, Menu> menuMap = getMenuMap();
        for (String key : menuMap.keySet()) {
            textTerminal.printf(MessageFormat.format("{0} ", TYPE));
            changeColor(MENU_COLOR);
            textTerminal.printf(key);
            changeColor(MANUAL_COLOR);
            Menu menu = menuMap.get(key);
            textTerminal.printf(MessageFormat.format(" {0}{1}", menu.getDescription()), System.lineSeparator());
        }
    }

    @Override
    public void printWrongInput() {
        changeColor(ERR_COLOR);
        textTerminal.println(WRONG_INPUT);
    }

    @Override
    public void choiceDiscountPolicy() {
        changeColor(SELECT_COLOR);
        Arrays.stream(DiscountPolicy.values())
                .forEach(
                        (p) -> textTerminal.print(MessageFormat.format(
                                "{0}. {1}{2}",
                                p.getPolicyId(),
                                p.getDescription(),
                                System.lineSeparator())
                        )
                );
    }

    @Override
    public void printAmount() {
        changeColor(SELECT_COLOR);
        textTerminal.println(PLEASE_AMOUNT);
    }

    @Override
    public void printAllList(List<VoucherEntity> repository) {
        repository.stream()
                .forEach((voucher) -> textTerminal.print(voucher.toString()));
    }

    @Override
    public void printException(Exception e) {
        changeColor(ERR_COLOR);
        textTerminal.println(e.getMessage());
    }

}
