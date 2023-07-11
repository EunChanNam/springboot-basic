package com.example.voucher.io;

import java.util.UUID;
import com.example.voucher.domain.Voucher;

class Writer {

    enum Message {
        SERVICE_TYPE_SELECTION("""
            === Voucher Program ===
            Type exit to exit the program.
            Type voucher to use voucherService.
            """),
        VOUCHER_SERVICE_TYPE_SELECTION("""
            === Voucher Program ===
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type remove to remove all vouchers.
            Type search_by_id to search voucher by id.
            """),
        VOUCHER_INFO_INPUT_REQUEST("""
            Select VoucherType And Info
              	"""),
        VOUCHER_TYPE_SELECTION("""
            * Input Number for select VoucherType
            1. FixedAmount
            2. PercentDiscount
              	"""),
        ID_INPUT_REQUEST("""
            * Input ID
            """),
        DISCOUNT_VALUE_INPUT_REQUEST("""
            * Input Discount Value
            """),
        INVALID_ARGUMENT("""
            유효하지 않은 값 입니다.              
            """),
        INVALID_ARGUMENT_RETRY_SERVICE_TYPE_SELECTION("""
            유효하지 않은 값 입니다. 서비스 타입을 다시 선택해주세요.
            """),
        INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION("""
            유효하지 않은 값 입니다. 모드 타입을 다시 선택해주세요.
            """),
        INVALID_ARGUMENT_CANT_CREATE_VOUCHER("""
            유효하지 않은 값입니다. 바우처를 생성할 수 없습니다.
            """),
        VOUCHER_CREATION_FAILED("""
            바우처 작업을 실패했습니다.
            """);

        private String text;

        Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Writer() {
    }

    public static final String VOUCHER_INFO_TEMPLATE = "VoucherID : %s, VoucherType : %s, discountValue : %d";

    public void writeMessage(UUID voucherId, Voucher.Type voucherType, long discountValue) {
        System.out.println(String.format(VOUCHER_INFO_TEMPLATE, voucherId.toString(), voucherType, discountValue));
    }

    public void writeMessage(Message message) {
        System.out.println(message.getText());
    }

}
