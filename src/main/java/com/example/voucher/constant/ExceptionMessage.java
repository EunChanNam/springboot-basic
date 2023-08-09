package com.example.voucher.constant;

public class ExceptionMessage {

    private ExceptionMessage() {
    }
    public static final String INVALID_ARGUMENT_RETRY_CUSTOMER_TYPE_SELECTION = """
        유효하지 않은 값 입니다. 고객 타입을 다시 선택해주세요.
        """;
    public static final String INVALID_ARGUMENT_RETRY_SERVICE_TYPE_SELECTION = """
        유효하지 않은 값 입니다. 서비스 타입을 다시 선택해주세요.
        """;
    public static final String INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION = """
        유효하지 않은 값 입니다. 모드 타입을 다시 선택해주세요.
        """;
    public static final String INVALID_ARGUMENT_CANT_CREATE_VOUCHER = """
        유효하지 않은 값입니다. 바우처를 생성할 수 없습니다.
        """;
    public static final String MESSAGE_ERROR_POSITIVE_CONSTRAINT = """
        값은 양수여야 합니다
        """;
    public static final String MESSAGE_ERROR_RANGE_CONSTRAINT = """
        퍼센트 값은 0과 100 사이여야 합니다
        """;
    public static final String FORMAT_ERROR_GREATER_THAN_CONSTRAINT = """
        값은 threshold 보다 커야합니다.
        """;

}
