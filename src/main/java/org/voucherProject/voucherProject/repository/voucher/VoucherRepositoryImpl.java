package org.voucherProject.voucherProject.repository.voucher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.entity.voucher.Voucher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//@Primary
public class VoucherRepositoryImpl implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher){
        if (findById(voucher.getVoucherId()).isPresent()) {
            throw new RuntimeException("동일한 아이디가 존재합니다.");
        }
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
