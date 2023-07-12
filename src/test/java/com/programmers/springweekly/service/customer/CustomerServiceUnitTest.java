package com.programmers.springweekly.service.customer;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import com.programmers.springweekly.service.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceUnitTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void update() {
        // given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(uuid, "changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);

        willDoNothing().given(customerRepository).update(customer);
    }

    @Test
    void findById() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);

        given(customerRepository.findById(customer.getCustomerId()))
                .willReturn(Optional.of(customer));

        // when
        customerService.findById(customer.getCustomerId());

        // then
        then(customerRepository).should(times(1)).findById(customer.getCustomerId());
    }

    @Test
    void finaAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "changhyeon1", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        Customer customer2 = new Customer(UUID.randomUUID(), "changhyeon2", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);

        given(customerRepository.findAll())
                .willReturn(List.of(customer1, customer2));

        // when
        customerService.findAll();

        // then
        then(customerRepository).should(times(1)).findAll();
    }


    @Test
    void getBlackList() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "changhyeon1", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        Customer customer2 = new Customer(UUID.randomUUID(), "changhyeon2", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);

        given(customerRepository.getBlackList())
                .willReturn(List.of(customer1, customer2));

        // when
        customerService.getBlackList();

        // then
        then(customerRepository).should(times(1)).getBlackList();
    }

    @Test
    void deleteById() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "changhyeon", "changhyeon.h@kakao.com", CustomerType.BLACKLIST);
        willDoNothing().given(customerRepository).deleteById(customer.getCustomerId());

        // when
        customerService.deleteById(customer.getCustomerId());

        // then
        then(customerRepository).should(times(1)).deleteById(customer.getCustomerId());
    }

    @Test
    void deleteAll() {
        // given
        willDoNothing().given(customerRepository).deleteAll();

        // when
        customerService.deleteAll();

        // then
        then(customerRepository).should(times(1)).deleteAll();
    }
}
