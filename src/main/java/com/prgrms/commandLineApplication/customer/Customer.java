package com.prgrms.commandLineApplication.customer;

import com.prgrms.commandLineApplication.customer.validator.CustomerValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

  private final UUID customerId;
  private final String email;
  private final LocalDateTime createdAt;
  private String name;

  private Customer(UUID customerId, String email, String name) {
    this.customerId = customerId;
    this.email = email;
    this.createdAt = LocalDateTime.now();
    this.name = name;
  }

  public static Customer of(UUID customerId, String email, String name) {
    CustomerValidator.checkId(customerId);
    CustomerValidator.checkEmail(email);
    return new Customer(customerId, email, name);
  }

}
