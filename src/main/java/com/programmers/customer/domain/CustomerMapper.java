package com.programmers.customer.domain;

import com.programmers.customer.dto.CustomerRequestDto;
import com.programmers.customer.dto.CustomerResponseDto;

//4차 PR
import java.time.LocalDateTime;

public class CustomerMapper {

    public static Customer convertRequestDtoToDomain(CustomerRequestDto requestDto) {
        return new Customer(requestDto.customerId(), requestDto.name(), LocalDateTime.now());
    }

    public static CustomerResponseDto convertDomainToResponseDto(Customer customer) {
        return new CustomerResponseDto(customer.getCustomerId(),
                customer.getName(),
                customer.getCreatedAt(),
                customer.getModifiedAt());
    }
}
