package prgms.spring_week1.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.io.message.ConsoleOutputMessage;
import prgms.spring_week1.menu.CustomerMenu;
import prgms.spring_week1.menu.Menu;
import prgms.spring_week1.menu.VoucherMenu;

import java.util.Scanner;

public class Input {
    private final Logger logger = LoggerFactory.getLogger(Input.class);
    private final Scanner sc = new Scanner(System.in);
    private final int MAXIMUM_NAME_LENGTH = 15;

    public void printConsoleMessage(String message) {
        System.out.println(message);
    }

    public VoucherType selectVoucherType() {
        System.out.println(ConsoleOutputMessage.TYPE_SELECT_MESSAGE);
        return VoucherType.findVoucherType(sc.nextLine());
    }

    public int insertDiscountValue() throws NumberFormatException {
        System.out.println(ConsoleOutputMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE);
        int inputValue = sc.nextInt();
        sc.nextLine();

        boolean isMinus = inputValue < 0;

        if (isMinus) {
            logger.warn("0보다 작은 수는 들어올 수 없습니다.");
            throw new NumberFormatException();
        }

        return inputValue;
    }

    public Menu getMenu() {
        System.out.println(ConsoleOutputMessage.MENU_LIST_MESSAGE);
        Menu menu = Menu.findMenuType(sc.nextLine());

        while (menu == null) {
            System.out.println(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            menu = Menu.findMenuType(sc.nextLine());
        }

        return menu;
    }

    public String inputVoucherType() {
        System.out.println(ConsoleOutputMessage.FIND_TYPE_SELECT_MESSAGE);
        return String.valueOf(VoucherType.findVoucherType(sc.nextLine()));
    }

    public VoucherMenu getVoucherMenu() {
        System.out.println(ConsoleOutputMessage.VOUCHER_MENU_LIST_MESSAGE);
        VoucherMenu menu = VoucherMenu.findMenuType(sc.nextLine());

        while (menu == null) {
            System.out.println(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            menu = VoucherMenu.findMenuType(sc.nextLine());
        }

        return menu;
    }

    public CustomerMenu getCustomerMenu() {
        System.out.println(ConsoleOutputMessage.CUSTOMER_MENU_LIST_MESSAGE);
        CustomerMenu menu = CustomerMenu.findMenuType(sc.nextLine());

        while (menu == null) {
            System.out.println(ConsoleOutputMessage.INVALID_MENU_MESSAGE);
            menu = CustomerMenu.findMenuType(sc.nextLine());
        }

        return menu;
    }

    public Email inputEmail() {
        System.out.println(ConsoleOutputMessage.INPUT_EMAIL_MESSAGE);

        try {
            return new Email(sc.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("이메일이 형식에 맞지 않습니다.");
            return inputEmail();
        }
    }

    public String[] inputCustomerInfo() {
        System.out.println(ConsoleOutputMessage.INPUT_NAME_AND_EMAIL_MESSAGE);
        return sc.nextLine().split(" ");
    }

    public String[] inputUpdateEmailInfo() {
        System.out.println(ConsoleOutputMessage.INPUT_BEFORE_EMAIL_AND_AFTER_EMAIL_MESSAGE);
        return sc.nextLine().split(" ");
    }

    public String inputName() {
        System.out.println(ConsoleOutputMessage.INPUT_CUSTOMER_NAME);
        String name = sc.nextLine();

        boolean isValidLength = name.length() > MAXIMUM_NAME_LENGTH;

        if (isValidLength) {
            System.out.println(ConsoleOutputMessage.INVALID_LENGTH_CUSTOMER_NAME);
            return inputName();
        }

        return name;
    }
}
