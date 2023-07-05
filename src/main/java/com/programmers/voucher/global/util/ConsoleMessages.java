package com.programmers.voucher.global.util;

public final class ConsoleMessages {
    //voucher program messages
    public static final String VOUCHER_PROGRAM = "=== Voucher Program ===";
    public static final String INPUT = "Type";
    public static final String EXIT_BEHAVIOR = "to exit the program.";
    public static final String HELP_BEHAVIOR = "to list command set.";
    public static final String CUSTOMER_BEHAVIOR = "to execute customer service.";
    public static final String VOUCHER_BEHAVIOR = "to execute voucher service.";
    public static final String EXIT_SERVICE_BEHAVIOR = "to move top menu.";

    public static final String CUSTOMER_SERVICE = "==== Customer Service ====";
    public static final String CUSTOMER_CREATE_BEHAVIOR = "to create new customer.";
    public static final String CUSTOMER_LIST_BEHAVIOR = "to list customers.";
    public static final String CUSTOMER_UPDATE_BEHAVIOR = "to update customer.";
    public static final String CUSTOMER_DELETE_BEHAVIOR = "to delete customer.";
    public static final String CUSTOMER_BLACKLIST_BEHAVIOR = "to list blacklist.";

    public static final String VOUCHER_SERVICE = "==== Voucher Service ====";
    public static final String VOUCHER_CREATE_BEHAVIOR = "to create a new voucher.";
    public static final String VOUCHER_LIST_BEHAVIOR = "to list all vouchers.";
    public static final String VOUCHER_DELETE_BEHAVIOR = "to delete a vouchers.";

    public static final String VOUCHER_TYPES = "Choose a type. [fixed] or [percent]";
    public static final String AMOUNT = "Enter an [amount]";
    public static final String PERCENT = "Enter a [percent]";

    public static final String ENTER_EMAIL = "Enter an [email]";
    public static final String ENTER_NAME = "Enter a [name]";
    public static final String ENTER_NEW_NAME = "Enter a new [name]";
    public static final String BAN = "ban";
    public static final String UNBAN = "unban";
    public static final String CHOOSE_BAN = "Ban the member? Choose [ban] or [unban]";
    public static final String ENTER_ID = "Enter an [ID]";

    public static final String CREATED_NEW_VOUCHER = "Created new voucher. VoucherId: {0}";
    public static final String DELETED_VOUCHER = "Deleted Voucher.";

    public static final String CREATED_NEW_CUSTOMER = "Created new Customer. CustomerId: {0}";
    public static final String UPDATED_CUSTOMER = "Updated customer info.";
    public static final String DELETED_CUSTOMER = "Deleted customer.";

    public static final String CUSTOMER_INFO = "[{0}] CustomerId: {1}, Email: {2}, Name: {3}";
    public static final String FIXED_VOUCHER_INFO = "VoucherId: {0}, Amount: {1}$";
    public static final String PERCENT_VOUCHER_INFO = "VoucherId: {0}, Amount: {1}$";

    public static final String EXIT_CONSOLE = "Bye Bye.";

    private ConsoleMessages() {
    }
}
