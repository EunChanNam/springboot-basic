package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.domain.customer.Customer;
import com.devcourse.springbootbasic.application.repository.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class CustomerServiceTest {

    @Test
    @DisplayName("블랙고객 리스트 반환 시 성공")
    void testGetAllBlackCustomers() {
        var customerRepositoryMock = mock(CustomerRepository.class);
        when(customerRepositoryMock.findAll()).thenReturn(
                List.of(
                        new Customer(0, "사과"),
                        new Customer(1, "딸기"),
                        new Customer(2, "포도"),
                        new Customer(3, "배")
                )
        );
        var sut = new CustomerService(customerRepositoryMock);
        var blackCustomers = sut.getBlackCustomers();
        assertThat(blackCustomers, notNullValue());
        assertThat(blackCustomers, not(empty()));
        assertThat(blackCustomers, instanceOf(List.class));
        assertThat(blackCustomers.get(0), instanceOf(Customer.class));
    }

}