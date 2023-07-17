package com.devcourse.voucherapp.entity.customer;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.exception.customer.CustomerTypeInputException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum CustomerType {
    NORMAL("1", "일반 고객"),
    BLACK("2", "블랙리스트 고객");

    private static final Map<String, CustomerType> customerTypeMap = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CustomerType::getOption, Function.identity())));

    private final String option;
    private final String name;

    CustomerType(String option, String name) {
        this.option = option;
        this.name = name;
    }

    public static CustomerType from(String customerTypeOption) {
        if (customerTypeMap.containsKey(customerTypeOption)) {
            return customerTypeMap.get(customerTypeOption);
        }

        throw new CustomerTypeInputException(customerTypeOption);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", option, name);
    }
}
