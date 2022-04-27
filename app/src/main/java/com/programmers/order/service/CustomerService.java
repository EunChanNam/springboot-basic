package com.programmers.order.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.domain.Customer;
import com.programmers.order.dto.IntegrationDto;
import com.programmers.order.dto.VocuherDto;
import com.programmers.order.exception.DomainException;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.message.ErrorLogMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.repository.customer.CustomerRepository;
import com.programmers.order.utils.TranslatorUtils;

@Service
@Transactional(readOnly = true)
public class CustomerService {

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
	private final CustomerRepository customerRepository;

	private final VoucherService voucherService;

	public CustomerService(CustomerRepository customerRepository, VoucherService voucherService,
			VoucherService voucherService1) {
		this.customerRepository = customerRepository;
		this.voucherService = voucherService1;
	}

	@Transactional
	public Optional<Customer> save(CustomerDto.SaveRequestDto saveRequestDto) {
		Customer customer = saveRequestDto.toCustomer();

		try {
			return Optional.of(customerRepository.insert(customer));
		} catch (JdbcException.NotExecuteQuery e) {
			log.info(ErrorLogMessage.getLogPrefix(), ErrorLogMessage.NOT_EXECUTE_QUERY);

			return Optional.empty();
		}
	}

	@Transactional
	public Optional<Customer> update(CustomerDto.UpdateCustomer updateCustomer) {
		String email = updateCustomer.getEmail();

		try {
			Customer customer = customerRepository.findByEmail(email)
					.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));
			customer.changeName(updateCustomer);

			return Optional.of(customerRepository.update(customer));
		} catch (JdbcException.NotExecuteQuery e) {
			log.info(ErrorLogMessage.getLogPrefix(), ErrorLogMessage.NOT_EXECUTE_QUERY);

			return Optional.empty();
		}
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Optional<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public boolean notExistByEmail(String email) {
		Optional<Customer> customer = customerRepository.findByEmail(email);

		return customer.isEmpty();
	}

	public Optional<Customer> findById(String uuid) {
		return customerRepository.findById(TranslatorUtils.toUUID(uuid.getBytes()));
	}

	public Optional<IntegrationDto.SaveRequestDto> registerVoucher(
			CustomerDto.RegisterVoucherDto registerVoucherDto) {

		Voucher voucher;
		Customer customer;

		try {
			voucher = voucherService.findById(registerVoucherDto.getVoucherId())
					.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));
			customer = this.findByEmail(registerVoucherDto.getEmail())
					.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));
		} catch (DomainException.NotFoundResource e) {
			log.error(ErrorLogMessage.getLogPrefix(), ErrorLogMessage.NOT_FOUND_RESOURCE);
			return Optional.empty();
		}

		// todo : 중간 테이블 생성하기


		return null;
	}

	public List<CustomerDto.ReponseDto> getCustomers(String voucherId) {
		Voucher voucher = voucherService.findById(UUID.fromString(voucherId))
				.orElseThrow(() -> new DomainException.NotFoundResource(
						ErrorMessage.CLIENT_ERROR));

		//todo : 중간테이블 조회 -> customer 조회!
		return null;
	}

	public List<VocuherDto.Response> lookUpWithVouchers(String email) {
		Customer customer = this.findByEmail(email)
				.orElseThrow(() -> new DomainException.NotFoundResource(ErrorMessage.CLIENT_ERROR));
		UUID id = customer.getId();
		// todo : 중간 테이블 조회 하기 -> voucher 정보들 모두 가져오기
		return null;
	}

	public boolean isNotExist(String voucherId) {
		return voucherService.isNotExist(voucherId);
	}

	// todo : 바우처 매핑,조회(고객이 가지고 있는 바우처 및 바우처를 보유한 고객 리스트),특정 바우처 제거

}
