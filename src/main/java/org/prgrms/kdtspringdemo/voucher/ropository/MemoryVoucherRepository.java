package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher == null) {
            throw new NoSuchElementException(NOT_FOUND_VOUCHER.getMessage());
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public Voucher update(Voucher voucher) {
        Voucher updatedVoucher = storage.putIfAbsent(voucher.getVoucherId(), voucher);

        return updatedVoucher == null ? voucher : updatedVoucher;
    }

    @Override
    public Voucher deleteById(UUID voucherId) {
        return storage.remove(voucherId);
    }
}
