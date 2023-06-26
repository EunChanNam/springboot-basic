package me.kimihiqq.vouchermanagement.repository;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Profile("prod")
@Repository
public class FileVoucherRepository implements VoucherRepository {
    private final String filePath = "vouchers.txt";

    @Override
    public Voucher save(Voucher voucher) {
        try (FileWriter fileWriter = new FileWriter(filePath, true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(voucher.getVoucherId() + "," + voucher.getType() + "," + voucher.getDiscount());
            writer.newLine();
            log.info("Voucher saved: {}", voucher.getVoucherId());
        } catch (IOException e) {
            log.error("Error saving voucher: {}", voucher.getVoucherId(), e);
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return findAll().stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Voucher voucher;
                if (parts[1].equalsIgnoreCase("Fixed")) {
                    voucher = new FixedAmountVoucher(UUID.fromString(parts[0]), parts[1], Long.parseLong(parts[2]));
                } else {
                    voucher = new PercentDiscountVoucher(UUID.fromString(parts[0]), parts[1], Long.parseLong(parts[2]));
                }
                vouchers.add(voucher);
            }
            log.info("All vouchers loaded");
        } catch (IOException e) {
            log.error("Error loading vouchers", e);
        }
        return vouchers;
    }

    @Override
    public void deleteById(UUID voucherId) {
        List<Voucher> vouchers = findAll();
        vouchers.removeIf(voucher -> voucher.getVoucherId().equals(voucherId));
        try (FileWriter fileWriter = new FileWriter(filePath);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (Voucher voucher : vouchers) {
                writer.write(voucher.getVoucherId() + "," + voucher.getType() + "," + voucher.getDiscount());
                writer.newLine();
            }
            log.info("Voucher deleted: {}", voucherId);
        } catch (IOException e) {
            log.error("Error deleting voucher: {}", voucherId, e);
        }
    }
}