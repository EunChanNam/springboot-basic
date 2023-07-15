package com.prgms.voucher.voucherproject.io;

public final class Constant {

    public static final String CONSOLE_APP_MENU = """
                Type **voucher** to start voucher program
                Type **customer** to start customer program
                Type **exit** to exit the program.""";

    public static final String CONSOLE_MENU = """
                === Voucher Program ===
                Type **exit** to exit voucher program.
                Type **create** to create a new voucher.
                Type **list** to list all vouchers.""";

    public static final String CONSOLE_VOUCHER_MENU = """
                1 -- 고정 할인 Voucher 생성
                2 -- 퍼센트 할인 Voucher 생성
                입력:  """;


    public static final String CREATE_FIXED_VOUCHER = "고정 할인 금액을 입력하세요. (1이상)";

    public static final String CREATE_PERCENT_VOUCHER = "퍼센트 할인 금액을 입력하세요. (1~99)";

    public static final String NOT_EXITS_VOUCHER = "존재하는 바우처가 없습니다.";

    public static final String WRONG_COMMAND = "잘못된 명령어입니다.";

    public static final String PROGRAM_END = "프로그램을 종료합니다.";

}
