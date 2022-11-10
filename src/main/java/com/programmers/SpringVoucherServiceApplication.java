package com.programmers;

import com.programmers.voucher.CommandLineApplication;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import static org.slf4j.LoggerFactory.*;

@SpringBootApplication
@ConfigurationPropertiesScan("com.programmers.voucher.config")
public class SpringVoucherServiceApplication {
    private static final Logger logger = getLogger(SpringVoucherServiceApplication.class);
    private static CommandLineApplication application;

    public SpringVoucherServiceApplication(CommandLineApplication application) {
        SpringVoucherServiceApplication.application = application;
    }

    public static void main(String[] args) {
        logger.info("애플리케이션 기동");
        SpringApplication.run(SpringVoucherServiceApplication.class, args);
        application.run();
        logger.info("애플리케이션 종료");
    }

}
