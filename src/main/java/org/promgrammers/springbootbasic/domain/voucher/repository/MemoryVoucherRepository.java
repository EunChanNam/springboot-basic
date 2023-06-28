package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.exception.DuplicateIDException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (storage.containsKey(voucher.getVoucherId())) {
            throw new DuplicateIDException("이미 저장되어 있는 Voucher ID 입니다.");
        }
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public Voucher update(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        if (!storage.containsKey(voucher)) {
            throw new IllegalArgumentException("해당 Voucher ID가 존재하지 않습니다. => " + voucherId);
        }
        storage.put(voucherId, voucher);
        return voucher;
    }

    @Override
    public void deleteAll() {
        this.storage.clear();
    }
}
