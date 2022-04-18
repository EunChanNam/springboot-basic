package com.prgrms.management.customer.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private CustomerType customerType;

    public Customer(UUID customerId, String name, String email,LocalDateTime createdAt,CustomerType customerType) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.customerType = customerType;
    }

    public Customer(CustomerRequest customerRequest) {
        this.customerId = UUID.randomUUID();
        this.name = customerRequest.getName();
        this.email = customerRequest.getEmail();
        this.createdAt = LocalDateTime.now();
        this.customerType = customerRequest.getCustomerType();
    }

    public Customer(CustomerType customerType) {
        this.customerId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.customerType = customerType;
    }

    private void validateName(String name) {
        if (name.isBlank())
            throw new RuntimeException("Name should not be blank");
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}