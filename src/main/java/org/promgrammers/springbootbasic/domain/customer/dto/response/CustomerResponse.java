package org.promgrammers.springbootbasic.domain.customer.dto.response;

import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;

import java.util.UUID;

public record CustomerResponse(UUID customerId, CustomerType customerType) {

    @Override
    public String toString() {
        return "블랙 리스트 유저 목록 [" +
                "customerId : '" + customerId + '\'' +
                ", customerType : '" + customerType + '\'' +
                ']';
    }
}
