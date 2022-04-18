package org.programmers.devcourse.voucher.engine.voucher.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.programmers.devcourse.voucher.EmbeddedDatabaseTestModule;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.FixedAmountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.PercentDiscountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.springframework.dao.DataAccessException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest extends EmbeddedDatabaseTestModule {


  final static List<Voucher> vouchersToTest = List.of(
      FixedAmountVoucher.factory.create(UUID.randomUUID(), 10000L),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 50),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 75),
      PercentDiscountVoucher.factory.create(UUID.randomUUID(), 11)
  );

  @BeforeAll
  void setup() {
    if (!mysql.isRunning()) {
      mysql.start();
    }
    repository = new JdbcVoucherRepository(getTestDataSource());
  }

  @Test
  @Order(1)
  @DisplayName("저장한 바우처가 없을 경우 비어 있는 컬렉션을 반환해야 한다.")
  void return_empty_collection_when_no_voucher_is_saved() {
    assertThat(repository.getAllVouchers()).isEmpty();

  }

  private JdbcVoucherRepository repository;

  @Test
  @Order(2)
  @DisplayName("save를 호출하면 voucher가 정확히 저장되어야 한다.")
  void save_proper_voucher() throws VoucherException {
    vouchersToTest.forEach(voucher -> {
      repository.save(voucher);
    });

    assertThat(repository.getAllVouchers()).containsAll(vouchersToTest);

  }

  @Test
  @DisplayName("voucherId로 원하는 voucher를 가져올 수 있어야 한다.")
  @Order(3)
  void get_proper_voucher_by_voucher_id() throws VoucherException {

    assertThat(repository.getAllVouchers()).containsAll(vouchersToTest);

    vouchersToTest.forEach(voucher -> {
      assertThat(repository.getVoucherById(voucher.getVoucherId())).isNotEmpty().get()
          .isEqualTo(voucher);
    });
  }


  @Test
  @DisplayName("transaction이 중간에 실패할 경우 rollback 되어야 한다.")
  @Order(4)
  void transaction_test() {
    assertThatThrownBy(() ->
        repository.runTransaction(() -> {
          repository.save(FixedAmountVoucher.factory.create(UUID.randomUUID(), 10000L));
          repository.save(vouchersToTest.get(0));
          repository.save(vouchersToTest.get(0));
          repository.save(vouchersToTest.get(0));
        })).isInstanceOf(DataAccessException.class);

    assertThat(repository.getAllVouchers().size()).isEqualTo(vouchersToTest.size());
  }

}
