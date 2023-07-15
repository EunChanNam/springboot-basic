package com.devcourse.springbootbasic.application.global.io;

public enum OutputMessage {

    START_GAME("=== Voucher Program ==="),
    END_GAME("프로그램을 종료합니다."),
    VOUCHER_CREATION_DONE("바우처가 생성되었습니다."),
    CUSTOMER_REGISTER_DONE("고객이 등록되었습니다."),
    CUSTOMER_MENU("--- 고객 관리 메뉴 선택 ---"),
    VOUCHER_MENU("--- 바우처 관리 메뉴 ---"),
    WALLET_MENU("--- 지갑 관리 메뉴 ---"),
    VOUCHER_TYPE("--- 바우처 타입 선택 ---"),
    LIST_VOUCHERS("- 생성한 바우처 목록입니다. -"),
    LIST_CUSTOMERS("- 고객 목록입니다. -"),
    LIST_BLACK_CUSTOMERS("- 진상 목록입니다. -");

    private final String messageText;

    OutputMessage(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

}
