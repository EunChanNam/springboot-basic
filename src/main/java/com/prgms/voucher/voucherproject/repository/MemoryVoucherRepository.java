package com.prgms.voucher.voucherproject.repository;

import com.prgms.voucher.voucherproject.domain.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId)); // null일 경우 empty 반환
    }

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public ArrayList<Voucher> findAll() {
        return new ArrayList<Voucher>(storage.values());
     }

}
