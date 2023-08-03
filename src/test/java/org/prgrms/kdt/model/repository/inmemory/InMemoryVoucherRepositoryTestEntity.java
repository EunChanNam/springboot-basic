package org.prgrms.kdt.model.repository.inmemory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;

class InMemoryVoucherRepositoryTestEntity {

	private static Stream<Arguments> vouchersProvider() {
		List<VoucherEntity> vouchers1 = Arrays.asList(
			new VoucherEntity(5L, 100, VoucherType.FixedAmountVoucher),
			new VoucherEntity(6L, 10, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(7L, 50, VoucherType.FixedAmountVoucher)
		);

		List<VoucherEntity> vouchers2 = Arrays.asList(
			new VoucherEntity(5L, 30, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(2L, 20, VoucherType.FixedAmountVoucher),
			new VoucherEntity(4L, 10, VoucherType.FixedAmountVoucher)
		);

		List<VoucherEntity> vouchers3 = Arrays.asList(
			new VoucherEntity(3L, 10, VoucherType.FixedAmountVoucher),
			new VoucherEntity(2L, 50, VoucherType.PercentDiscountVoucher),
			new VoucherEntity(1L, 30, VoucherType.PercentDiscountVoucher)
		);

		return Stream.of(
			Arguments.of(vouchers1),
			Arguments.of(vouchers2),
			Arguments.of(vouchers3)
		);
	}

	@ParameterizedTest
	@DisplayName("인메모리에서 voucher를 저장하고 불러올 수 있다.")
	@MethodSource("vouchersProvider")
	void createVoucherTest(List<VoucherEntity> voucherEntities) {
		// given
		Map<Long, VoucherEntity> store = new HashMap<>();
		VoucherRepository voucherRepository = new InMemoryVoucherRepository(store);

		//when
		voucherEntities.stream()
			.forEach(voucher -> voucherRepository.createVoucher(voucher));
		List<VoucherEntity> expectedVoucherEntities = voucherRepository.findAll()
			.stream()
			.collect(Collectors.toList());

		//then
		Assertions.assertThat(voucherEntities)
			.containsExactlyInAnyOrderElementsOf(expectedVoucherEntities);
	}
}