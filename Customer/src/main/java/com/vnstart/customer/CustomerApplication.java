package com.vnstart.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.vnstart.customer.*","com.vnstart.library.*"})
@EnableJpaRepositories(value = "com.vnstart.library.repository")
@EntityScan(value = "com.vnstart.library.model")
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

}
