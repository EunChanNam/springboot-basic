package com.devcourse.voucherapp.entity.customer;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.CustomerException;
import com.devcourse.voucherapp.exception.ExceptionRule;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public enum CustomerMenu {
    CREATE("1", "새 고객 생성"),
    READ_ALL("2", "전체 고객 조회"),
    UPDATE("3", "고객 수정"),
    DELETE("4", "고객 삭제"),
    READ_BLACK_LIST("5", "블랙리스트 고객 조회"),
    HOME("home", "홈으로 이동");

    private static final Map<String, CustomerMenu> customerMenuMap = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CustomerMenu::getOption, Function.identity())));

    @Getter
    private final String option;

    private final String name;

    CustomerMenu(String option, String name) {
        this.option = option;
        this.name = name;
    }

    public static CustomerMenu from(String menuOption) {
        if (customerMenuMap.containsKey(menuOption)) {
            return customerMenuMap.get(menuOption);
        }

        throw new CustomerException(ExceptionRule.MENU_INVALID, menuOption);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", option, name);
    }
}
