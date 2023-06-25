package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.util.GeneratorDeepCopiedType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository{

    private final GeneratorDeepCopiedType generatorDeepCopiedType;
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    public MemoryVoucherRepository(GeneratorDeepCopiedType generatorDeepCopiedType) {
        this.generatorDeepCopiedType = generatorDeepCopiedType;
    }

    @Override
    public void saveVoucher(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> getVoucherMap() {
        return generatorDeepCopiedType.copiedMap(voucherMap);
    }
}
