package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.*;
import com.programmers.springweekly.dto.ReadVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    @Value("${file.voucher.path}")
    private String file_path;
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Override
    public void saveVoucher(Voucher voucher) {
      try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file_path, true));) {

          String voucherId = String.valueOf(voucher.getVoucherId());
          String voucherAmount = String.valueOf(voucher.getVoucherAmount());

          bufferedWriter.write(voucherId);
          bufferedWriter.write(",");
          bufferedWriter.write(voucherAmount);
          bufferedWriter.write(",");
          bufferedWriter.write(voucher.getVoucherType().getVoucherTypeString());
          bufferedWriter.newLine();

          bufferedWriter.flush();
      } catch(Exception e){
          logger.error("error message: {}", e.getMessage());
      }
    }

    @Override
    public Map<UUID, Voucher> getVoucherMap() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file_path));
            String line = "";

            while((line = bufferedReader.readLine()) != null){
                String[] readLine = line.split(",");

                ReadVoucherDto readVoucherDto = new ReadVoucherDto(readLine[0], readLine[1], readLine[2]);

                Voucher voucher = createVoucher(readVoucherDto);

                voucherMap.put(readVoucherDto.getVoucherId(), voucher);
            }
        } catch (Exception e){
            logger.error("error message: {}", e.getMessage());
        }

        return voucherMap;
    }

    private Voucher createVoucher(ReadVoucherDto readVoucherDto){
        if(readVoucherDto.getVoucherType() == VoucherType.FIXED){
            return VoucherFactory.createVoucher(VoucherType.FIXED, readVoucherDto.getDiscountAmount());
        }

        return VoucherFactory.createVoucher(VoucherType.PERCENT, readVoucherDto.getDiscountAmount());
    }
}
