package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.FixedVoucherPolicy;
import com.prgms.VoucherApp.domain.voucher.PercentVoucherPolicy;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class VoucherFileStorage implements VoucherStorage {

    private static final Logger log = LoggerFactory.getLogger(VoucherFileStorage.class);
    private final Map<UUID, Voucher> voucherCache = new ConcurrentHashMap<>();

    @Value("${voucher.file.path}")
    private String filePath;

    @PostConstruct
    public void initVoucherMap() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath));
            String voucherRecord = "";
            while ((voucherRecord = bufferedReader.readLine()) != null) {
                VoucherDto voucherDto = createVoucherDto(voucherRecord);
                Voucher voucher = createVoucher(voucherDto);
                voucherCache.put(voucher.getVoucherId(), voucher);
            }
        } catch (IOException e) {
            log.error("initVoucherMap() method Exception, message : {}", e.getMessage());
            throw new RuntimeException("Voucher 파일을 불러오던 중 IO 초기화 문제가 발생하였습니다.", e);
        }
    }

    @Override
    public void save(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.write(voucher.getVoucherId().toString());
            bufferedWriter.write(",");
            bufferedWriter.write(voucher.getVoucherPolicy().getDiscountAmount().toString());
            bufferedWriter.write(",");
            bufferedWriter.write(voucher.getVoucherType().getVoucherTypeName());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            voucherCache.put(voucher.getVoucherId(), voucher);
        } catch (IOException e) {
            log.error("{} Failed to write the content to the file. errorMessage : {}", getClass().getEnclosingMethod().getName(), e.getMessage());
        }
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        Voucher voucher = voucherCache.get(voucherId);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherCache.values()
            .stream()
            .toList();
    }

    private VoucherDto createVoucherDto(String voucherRecord) {
        String[] voucherField = voucherRecord.split(",");
        UUID voucherId = UUID.fromString(voucherField[0]);
        BigDecimal discountAmount = BigDecimal.valueOf(Double.parseDouble(voucherField[1]));
        VoucherType voucherType = VoucherType.findByVoucherTypeName(voucherField[2]);
        return new VoucherDto(voucherId, voucherType, discountAmount);
    }

    private Voucher createVoucher(VoucherDto voucherDto) {
        return switch (voucherDto.getVoucherType()) {
            case FIXED_VOUCHER -> new Voucher(voucherDto.getVoucherId(),
                new FixedVoucherPolicy(voucherDto.getDiscountAmount()),
                voucherDto.getVoucherType());
            case PERCENT_VOUCHER -> new Voucher(voucherDto.getVoucherId(),
                new PercentVoucherPolicy(voucherDto.getDiscountAmount()),
                voucherDto.getVoucherType());
        };
    }
}
