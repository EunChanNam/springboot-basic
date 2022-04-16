package com.example.voucherproject.user.domain;

import com.example.voucherproject.common.enums.UserType;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UserFactory {

    public User create(String userName, UserType userType) {
        return new User(UUID.randomUUID(),userType, userName);
    }

}
