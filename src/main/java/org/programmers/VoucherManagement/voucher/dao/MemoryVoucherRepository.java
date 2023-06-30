package org.programmers.VoucherManagement.voucher.dao;

import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> map = new LinkedHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        map.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return map.values()
                .stream()
                .toList();
    }
}
