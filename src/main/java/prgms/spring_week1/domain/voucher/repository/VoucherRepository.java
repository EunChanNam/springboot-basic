package prgms.spring_week1.domain.voucher.repository;

import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.voucher.model.Voucher;

import java.util.List;

@Repository
public interface VoucherRepository {
    List<Voucher> findAll();

    void insert(Voucher voucher);

    List<Voucher> findByType(String voucherType);

    void delete();
}
