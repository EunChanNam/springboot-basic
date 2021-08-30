package org.programmers.kdt.voucher.repository;


import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.PercentDiscountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository  {
    // FIXME : 데이터 양이 많아지면 메모리를 너무 많이 차지하거나 오류가 발생할 수 있음.
    private final Map<UUID, Voucher> cache = new ConcurrentHashMap<>();
    // TODO : JSON 파일에 읽고 쓰도록 바꾸기
    // TODO : YAML 파일로부터 경로 및 파일명을 읽어들여 전달해주는 VoucherPropeties 클래스를 정의하고 이곳을 통해 path와 file을 받아오도록 수정하기
    private final File file = new File("VoucherData.txt");

    // TODO : 각 class 별로 로거를 두지 않고 AOP 적용
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Cannot create voucher data file -> {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while (null != (line = bufferedReader.readLine())) {
                String[] data = line.split(" ");
                UUID uuid = UUID.fromString(data[0]);
                VoucherType voucherType = VoucherType.of(data[1]);
                long discount = Long.parseLong(data[2]);

                switch (voucherType) {
                    case FIXED -> cache.put(uuid, new FixedAmountVoucher(uuid, discount));
                    case PERCENT -> cache.put(uuid, new PercentDiscountVoucher(uuid, discount));
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            logger.error("Something went wrong during reading voucher data file -> {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(cache.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            if (null == cache.put(voucher.getVoucherId(), voucher)) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                bufferedWriter.write(voucher.getVoucherId() + " " + voucher.getVoucherType() + " " + voucher.getDiscount());
                bufferedWriter.newLine();
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            logger.error("Inserting new voucher Fails -> {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return voucher;
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        if (null != cache.remove(voucherId)) {
            // FIXME : 매번 파일 전체를 다시 써야 하므로 성능 저하 발생. 해결 필요
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));

                for (Voucher voucher : cache.values()) {
                    bufferedWriter.write(voucher.getVoucherId() + " " + voucher.getVoucherType() + " " + voucher.getDiscount());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("Deleting a voucher Fails -> {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(cache.values());
    }
}
