package prgms.spring_week1.io;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.exception.NoSuchOptionValueException;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;
import prgms.spring_week1.menu.Menu;

import java.util.Scanner;

public class Input {
    private final Scanner sc = new Scanner(System.in);

    public String input() {
        return sc.nextLine();
    }

    public Menu selectMenu() {
        String selectOption = this.input();
        return Menu.findMenuType(selectOption);
    }

    public Long insertFixedDiscountValue() {

        Long discountValue = VoucherType.validateAmountInputValue(Long.parseLong(this.input()));

        if (discountValue == null) {
            return insertPercentDiscountValue();
        }

        return discountValue;
    }

    public Long insertPercentDiscountValue() {
        Long discountValue = VoucherType.validatePercentInputValue(Long.parseLong(this.input()));

        if (discountValue == null) {
            return insertPercentDiscountValue();
        }

        return discountValue;
    }

}
