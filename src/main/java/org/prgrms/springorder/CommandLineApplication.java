package org.prgrms.springorder;

import org.prgrms.springorder.global.CommandLineController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("org.prgrms.springorder.config")
@SpringBootApplication
public class CommandLineApplication implements CommandLineRunner {

    private final CommandLineController controller;

    public CommandLineApplication(CommandLineController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandLineApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.run();
    }

}
