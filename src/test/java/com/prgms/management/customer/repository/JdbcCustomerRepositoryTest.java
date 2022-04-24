package com.prgms.management.customer.repository;

import com.prgms.management.customer.exception.CustomerException;
import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ActiveProfiles("default")
@DisplayName("JdbcCustomerRepositoryTest 유닛 테스트")
class JdbcCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    EmbeddedMysql embeddedMysql;
    List<Customer> customers = new ArrayList<>();

    @BeforeAll
    void setUp() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1234")
                .withTimeZone("Asia/Seoul")
                .withTimeout(2, TimeUnit.MINUTES)
                .build();

        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("demo", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Configuration
    @ComponentScan
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/demo")
                    .username("test")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @DisplayName("save() 테스트")
    @Nested
    @Order(1)
    class SaveTest {
        @DisplayName("고객 정보 저장 성공 사례")
        @ParameterizedTest(name = "{0} 고객 정보 저장")
        @CsvSource({"kate, WHITE, kate@gmail.com", "jade, WHITE, jade@gmail.com", "sage, BLACK, sage@gmail.com", "haes, BLACK, haes@gmail.com"})
        void saveSuccess(String name, String typeStr, String email) {
            Customer newCustomer = new Customer(name, CustomerType.valueOf(typeStr), email);
            customers.add(customerRepository.save(newCustomer));

            var retrievedCustomer = customerRepository.findById(newCustomer.getId());
            assertThat(retrievedCustomer, not(nullValue()));
            assertThat(retrievedCustomer, equalTo(newCustomer));
        }

        @DisplayName("고객 정보 저장 실패 사례")
        @Test
        void saveFail() {
            assertThrows(CustomerException.class, () -> {
                Customer newCustomer = new Customer("test", CustomerType.NONE, "test@test.co");
                customers.add(customerRepository.save(newCustomer));
            });
        }
    }

    @DisplayName("findById() 테스트")
    @Nested
    @Order(2)
    class FindByIdTest {
        @DisplayName("아이디를 통한 조회 가능 사례")
        @Test
        void findSuccess() {
            for (Customer customer : customers) {
                var resultCustomer = customerRepository.findById(customer.getId());
                assertThat(resultCustomer, equalTo(customer));
            }
        }

        @DisplayName("아이디를 통한 조회 실패 사례")
        @Test
        void findFail() {
            assertThat(customerRepository.findById(UUID.randomUUID()), is(nullValue()));
        }
    }

    @DisplayName("findByEmail() 테스트")
    @Nested
    @Order(2)
    class FindByEmailTest {
        @DisplayName("이메일을 통한 조회 가능 사례")
        @Test
        void findSuccess() {
            for (Customer customer : customers) {
                var resultCustomer = customerRepository.findByEmail(customer.getEmail());
                assertThat(resultCustomer, equalTo(customer));
            }
        }

        @DisplayName("이메일을 통한 조회 실패 사례")
        @Test
        void findFail() {
            assertThat(customerRepository.findByEmail("fake@email"), is(nullValue()));
        }
    }

    @DisplayName("findAll() 테스트")
    @Nested
    @Order(3)
    class FindAllTest {
        @DisplayName("전체 고객 정보 조회 가능 사례")
        @Test
        void findSuccess() {
            var resultCustomers = customerRepository.findAll();
            assertThat(resultCustomers.isEmpty(), is(false));
            assertThat(resultCustomers, hasSize(customers.size()));
        }
    }

    @DisplayName("findByType() 테스트")
    @Nested
    @Order(3)
    class FindByTypeTest {
        @DisplayName("타입에 따른 고객 정보 조회 가능 사례")
        @ParameterizedTest(name = "{0} 고객 정보 저장")
        @CsvSource({"WHITE, 2", "BLACK, 2"})
        void findSuccess(String typeStr, int count) {
            var resultCustomers = customerRepository.findByType(CustomerType.valueOf(typeStr));
            assertThat(resultCustomers.isEmpty(), is(false));
            assertThat(resultCustomers, hasSize(count));
        }

        @DisplayName("타입에 따른 고객 정보 조회 실패 사례")
        @Test
        void findFail() {
            var resultCustomers = customerRepository.findByType(CustomerType.NONE);
            assertThat(resultCustomers.isEmpty(), is(true));
        }
    }
}