package com.kdt.commandLineApp.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toCollection;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> map = new ConcurrentHashMap<>();

    @Override
    public void add(Voucher voucher) {
        map.put(voucher.getId(), voucher);
    }

    @Override
    public Optional<Voucher> get(String id) {
        return Optional.ofNullable(map.get(UUID.fromString(id)));
    }

    @Override
    public List<Voucher> getType(String type) {
        return map.values().stream().filter((e)-> e.getType().equals(type)).toList();
    }

    @Override
    public List<Voucher> getAll() {
        return map.values()
                .stream()
                .collect(toCollection(ArrayList::new));
    }

    @Override
    public void remove(String id) {
        map.remove(UUID.fromString(id));
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public void destroy() throws Exception {

    }
}
