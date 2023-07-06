package com.vnstart.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.vnstart.library.*","com.vnstart.admin.*"})
@EnableJpaRepositories(value = "com.vnstart.library.repository")
@EntityScan(value = "com.vnstart.library.model")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
