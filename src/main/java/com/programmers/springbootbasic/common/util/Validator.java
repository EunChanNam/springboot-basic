package com.programmers.springbootbasic.common.util;

import java.util.UUID;

public final class Validator {
    private static final String ID_IS_NULL = "빈 값이나 공백을 아이디로 사용할 수 없습니다. 현재 값: ";
    private static final String AMOUNT_OR_DISCOUNT_IS_NULL = "빈 값이나 공백을 할인률/할인액으로 지정할 수 없습니다. 현재 값: ";
    private static final String NULL_OR_BLANK = "빈 값이나 공백을 사용할 수 없습니다. 현재 값: ";
    private static final String EMAIL_IS_WRONG_OR_NULL = "빈 값이나 유효하지 않은 이메일입니다. 현재 값: ";


    private Validator() {
    }

    public static void checkNullUuid(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException(ID_IS_NULL + uuid);
        }
    }

    public static void checkNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(NULL_OR_BLANK + input);
        }
    }

    public static void checkNullNumber(Number number) {
        if (number == null) {
            throw new IllegalArgumentException(AMOUNT_OR_DISCOUNT_IS_NULL + number);
        }
    }

    public static void checkNullOrWrongEmail(String input) {
        if (input == null || !PatternUtils.isEmail(input) || input.isBlank()) {
            throw new IllegalArgumentException(EMAIL_IS_WRONG_OR_NULL + input);
        }
    }
}
