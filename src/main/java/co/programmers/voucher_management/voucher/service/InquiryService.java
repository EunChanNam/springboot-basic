package co.programmers.voucher_management.voucher.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.voucher.dto.Response;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.repository.VoucherRepository;

@Service
public class InquiryService {
	private final VoucherRepository voucherRepository;

	private InquiryService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	private static VoucherResponseDTO mapToDResponseDTO(Voucher voucher) {
		return VoucherResponseDTO.builder()
				.id(voucher.getId())
				.discountType(voucher.getDiscountStrategy().getType())
				.discountAmount(voucher.getDiscountStrategy().getAmount())
				.build();
	}

	public Response run() {
		List<Voucher> inquiriedData = voucherRepository.findAll();
		List<VoucherResponseDTO> voucherResponseDTOs = inquiriedData.stream()
				.map(InquiryService::mapToDResponseDTO)
				.collect(Collectors.toList());
		return Response.builder()
				.state(Response.State.SUCCESS)
				.responseData(voucherResponseDTOs)
				.build();
	}

}
