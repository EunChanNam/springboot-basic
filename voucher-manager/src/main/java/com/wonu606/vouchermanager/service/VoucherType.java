package com.wonu606.vouchermanager.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static Optional<VoucherType> getVoucherTypeByName(String name) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.name().equals(name.toUpperCase()))
                .findFirst();
    }

    public static List<String> getAllNames() {
        return Arrays.stream(VoucherType.values())
                .map(v -> v.name().toLowerCase())
                .collect(Collectors.toList());
    }
}
