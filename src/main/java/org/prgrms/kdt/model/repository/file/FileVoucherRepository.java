package org.prgrms.kdt.model.repository.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.VoucherRuntimeException;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.util.FileIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
@Qualifier("FileVoucherRepository")
public class FileVoucherRepository implements VoucherRepository {

	private final ObjectMapper objectMapper;
	private final FileIO fileIO;
	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	public FileVoucherRepository(ObjectMapper objectMapper, FileIO fileIO) {
		this.objectMapper = objectMapper;
		this.fileIO = fileIO;
	}

	@Override
	public VoucherEntity saveVoucher(VoucherEntity voucherEntity) {
		try {
			String voucherJson = objectMapper.writeValueAsString(voucherEntity);
			fileIO.saveStringToFile(voucherJson + System.lineSeparator());
			return voucherEntity;
		} catch (JsonProcessingException ex) {
			logger.error("voucher entity id is {}", voucherEntity.getVoucherId());
			logger.error(ErrorCode.VOUCHER_CREATE_FAIL.getErrorMessage(), ex);
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_CREATE_FAIL);
		}
	}

	@Override
	public List<VoucherEntity> findAllEntities() {
		String fileAllText = fileIO.loadStringFromFile();
		List<VoucherEntity> voucherEntities = toVoucherEntities(fileAllText);
		return voucherEntities;
	}

	private List<VoucherEntity> toVoucherEntities(String fileAllText) {
		List<VoucherEntity> voucherEntities = new ArrayList<>();

		Arrays.stream(fileAllText.split(System.lineSeparator()))
			.forEach(line -> {
				try {
					VoucherEntity voucherEntity = objectMapper.readValue(line, VoucherEntity.class);
					voucherEntities.add(voucherEntity);
				} catch (JsonProcessingException e) {
					logger.error("readAll 메서드에서 파일 불러오기 실패" + e.toString());
				}
			});
		return voucherEntities;
	}

	@Override
	public VoucherEntity updateVoucher(VoucherEntity voucherEntity) {
		throw new UnsupportedOperationException("아직 미 구현한 기능 입니다.");
	}

	@Override
	public VoucherEntity findVoucherById(Long voucherId) {
		String fileAllText = fileIO.loadStringFromFile();
		List<VoucherEntity> voucherEntities = toVoucherEntities(fileAllText);
		return voucherEntities
			.stream()
			.filter(voucherEntity -> voucherEntity.getVoucherId().equals(voucherId))
			.findFirst()
			.orElseThrow(
				() -> {
					logger.error("voucher entity id is {}", voucherId);
					logger.error(ErrorCode.VOUCHER_ID_NOT_FOUND.getErrorMessage());
					throw new VoucherRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
				}
			);
	}

	@Override
	public void deleteVoucherById(Long voucherId) {
		throw new UnsupportedOperationException("아직 미 구현한 기능 입니다.");
	}
}
