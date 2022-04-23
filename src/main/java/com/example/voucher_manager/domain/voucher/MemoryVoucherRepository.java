package com.example.voucher_manager.domain.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> memory = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memory.values());
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try {
            memory.put(voucher.getVoucherId(), voucher);
        } catch (RuntimeException e){
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    public void clear() {
        memory.clear();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if (memory.containsKey(voucherId)) {
            return Optional.of(memory.get(voucherId));
        }
        return Optional.empty();
    }

    @Override
    public Voucher update(Voucher voucher) {
        if (memory.containsKey(voucher.getVoucherId())) {
            memory.put(voucher.getVoucherId(), voucher);
        }
        return voucher;
    }

}
