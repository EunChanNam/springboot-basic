package com.mountain.voucherApp.constants;

public class Message {

    // discount
    public static final String FIXED_DISCOUNT = "고정 할인";
    public static final String PERCENT_DISCOUNT = "비율 할인";
    public static final String FIXED_UINT = "원";
    public static final String PERCENT_UNIT = "%";

    public static final String FILE_INSERT_ERROR = "file insert error";
    public static final String FILE_READ_ERROR = "file read error";
    public static final String NEGATIVE_AMOUNT_ERROR = "0 이하의 수는 입력할 수 없습니다.";
    public static final String MAX_MORE_ERROR = "최대 금액을 넘을 수 없습니다: ";
    public static final String EMPTY_RESULT_ERROR = "결과가 존재하지 않습니다.";
    public static final String CREATE_NEW_FILE = "새로운 파일 생성.";
    public static final String CREATE_NEW_VOUCHER = "새로운 바우처 생성";
    public static final String PROGRAM_EXIT = "프로그램 종료";
    // menu
    public static final String EXIT = "exit";
    public static final String CREATE = "create";
    public static final String LIST = "list";
    public static final String ADD_VOUCHER = "add_voucher";
    public static final String CUSTOMER_LIST = "customer_list";
    public static final String REMOVE_VOUCHER = "remove_voucher";
    public static final String LIST_BY_VOUCHER = "list_by_voucher";

    public static final String EXIT_PROGRAM_DESC = "프로그램 종료.";
    public static final String CREATE_VOUCHER_DESC = "새로운 바우처 생성.";
    public static final String LIST_VOUCHERS_DESC = "바우처 목록 조회.";
    public static final String ADD_VOUCHER_DESC = "고객 바우처 등록.";
    public static final String CUSTOMER_LIST_DESC = "고객 정보 조회.";
    public static final String REMOVE_VOUCHER_DESC = "고객 바우처 제거.";
    public static final String LIST_BY_VOUCHER_DESC = "바우처 별 고객 조회.";
    // console
    public static final String MANUAL_TITLE = "=== Voucher Program ===";
    public static final String WRONG_INPUT = "올바르지 않은 입력입니다.";
    public static final String PLEASE_AMOUNT = "바우처에 적용 될 할인금액(할인율)을 입력해 주세요.";
    public static final String EMPTY_DATA = "등록 된 데이터가 없습니다.";
    // jdbc
    public static final String NOT_INSERTED = "Insert가 수행되지 않았습니다.";
    public static final String NOT_UPDATED = "Update가 수행되지 않았습니다.";
    public static final String EMPTY_RESULT = "조회 결과가 존재하지 않습니다.";
    public static final String EXIST_VOUCHER = "존재하는 바우처 입니다.";
    // format
    public static final String CUSTOMER_BY_VOUCHER_FORMAT = "[{0}] Voucher가 보유한 고객 리스트.";

}
