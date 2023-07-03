package com.programmers.voucher.domain.customer.domain;

import com.programmers.voucher.domain.customer.dto.CustomerDto;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private String name;
    private boolean banned = false;

    public Customer(UUID customerId, String email, String name) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;
    }

    public CustomerDto toDto() {
        return new CustomerDto(customerId, email, name, banned);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void ban() {
        this.banned = true;
    }

    public void unban() {
        this.banned = false;
    }

    public String fullInfoString() {
        return "customerId: " + customerId + ", name: " + name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
