package com.programmers.console.view;

import com.programmers.console.util.Menu;
import java.util.Scanner;

public class Console implements InputView, OutputView {

    private static final String arrow = "> ";
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String getRequest() {
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public void printMenu() {
        println(Menu.COMMAND_MESSAGE.getMessage());
        print(arrow);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }
}