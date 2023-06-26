package com.programmers.voucher.repository;

import com.programmers.global.exception.NotFoundException;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherEntity;
import com.programmers.voucher.domain.VoucherMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, VoucherEntity> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        VoucherEntity voucherEntity = VoucherMapper.domainToEntity(voucher);
        storage.put(voucherEntity.getVoucherId(), voucherEntity);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        if (storage.isEmpty()) {
            throw new NotFoundException();
        }
        return storage.values().stream().map(VoucherMapper::entityToDomain).toList();
    }

    @Override
    public Voucher findById(UUID voucherId) {
        if (!storage.containsKey(voucherId)) throw new NotFoundException();
        return VoucherMapper.entityToDomain(storage.get(voucherId));

    }
}
