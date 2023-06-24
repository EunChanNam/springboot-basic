package com.devcourse.voucherapp.view;

public interface OutputView {

    void showMenu();

    void showQuitMessage();

    void showVoucherTypes();

    void showFixDiscountPriceInputMessage();

    void printWithLineBreak();

    void printWithLineBreak(String data);

    void printWithoutLineBreak(String data);
}
