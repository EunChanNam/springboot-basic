package org.weekly.weekly.voucher.repository;

import org.springframework.stereotype.Repository;
import org.weekly.weekly.voucher.domain.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherRepository {
    private final Map<UUID, Voucher> storages = new ConcurrentHashMap<>();

    public Voucher insert(Voucher voucher) {
        storages.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storages.get(voucherId));
    }
}
