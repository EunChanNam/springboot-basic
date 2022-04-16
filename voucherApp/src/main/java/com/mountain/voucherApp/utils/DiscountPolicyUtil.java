package com.mountain.voucherApp.utils;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.voucher.Voucher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DiscountPolicyUtil {

    private DiscountPolicyUtil() {}

    private static final Map<Integer ,DiscountPolicy> map = new ConcurrentHashMap<>();

    public static Voucher getVoucher(int policyId) {
        Map<Integer, DiscountPolicy> discountPolicyMap = getDiscountPolicyMap();
        DiscountPolicy discountPolicy = discountPolicyMap.get(policyId);
        return discountPolicy.getVoucher();
    }

    public static Map<Integer,DiscountPolicy> getDiscountPolicyMap() {
        if (map.size() == 0) {
            Arrays.stream(DiscountPolicy.values())
                    .forEach((dp) -> {
                        map.put(dp.getPolicyId(), dp);
                    });
        }
        return map;
    }
}
