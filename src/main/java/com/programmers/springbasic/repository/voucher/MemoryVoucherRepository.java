package com.programmers.springbasic.repository.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.voucher.Voucher;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

	private final Map<UUID, Voucher> storage;

	public MemoryVoucherRepository() {
		storage = new ConcurrentHashMap<>();
	}

	@Override
	public Voucher save(Voucher voucher) {
		storage.put(voucher.getVoucherId(), voucher);
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		List<Voucher> list = new ArrayList<>();
		storage.forEach((uuid, voucher) -> list.add(voucher));
		return list;
	}
}
