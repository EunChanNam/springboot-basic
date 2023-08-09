package prgms.spring_week1.domain.voucher.repository.impl;

import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherList = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return voucherList.values()
                .stream()
                .toList();
    }

    @Override
    public void insert(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        return voucherList
                .values()
                .stream()
                .filter(v -> v.getVoucherType().toString() == voucherType)
                .toList();
    }

    @Override
    public void delete() {
        voucherList.clear();
    }
}
