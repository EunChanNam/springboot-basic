package org.prgrms.orderapp.service;

import org.prgrms.orderapp.model.FixedAmountVoucher;
import org.prgrms.orderapp.model.PercentDiscountVoucher;
import org.prgrms.orderapp.model.Voucher;
import org.prgrms.orderapp.model.VoucherType;
import org.prgrms.orderapp.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Cannot find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {

    }

    public Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Optional<Voucher> createVoucher(String type, String value) {
        logger.info("VoucherService.createVoucher() is called with args: {}, {}", type, value);
        if (VoucherType.isValid(type, value))  {
            // TODO:: Use Strategy Pattern!
            long amount = Long.parseLong(value);
            if (type.equalsIgnoreCase(VoucherType.FIXED.name()))
                return Optional.of(new FixedAmountVoucher(UUID.randomUUID(), amount));
            else if (type.equalsIgnoreCase(VoucherType.PERCENT.name()))
                return Optional.of(new PercentDiscountVoucher(UUID.randomUUID(), amount));
        }
        return Optional.empty();
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

}
