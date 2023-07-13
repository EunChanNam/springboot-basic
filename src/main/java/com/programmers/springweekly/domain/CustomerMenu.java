package com.programmers.springweekly.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum CustomerMenu {
    CREATE,
    UPDATE,
    DELETE,
    SELECT,
    BLACKLIST;

    public static CustomerMenu from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Input : {}, 사용자가 입력한 고객 메뉴가 존재하지 않아서 발생한 예외, {} ", type, e.getMessage());
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 고객 메뉴가 없습니다.");
        }
    }

}
