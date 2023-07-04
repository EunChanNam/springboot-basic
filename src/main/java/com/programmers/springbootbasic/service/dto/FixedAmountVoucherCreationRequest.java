package com.programmers.springbootbasic.service.dto;

import java.time.LocalDateTime;

public record FixedAmountVoucherCreationRequest(
        String type,
        String name,
        Long minimumPriceCondition,
        LocalDateTime createdDate,
        LocalDateTime expirationDate,
        int amount
) {
}
