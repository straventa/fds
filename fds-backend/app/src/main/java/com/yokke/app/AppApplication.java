package com.yokke.app;

import com.yokke.fdsservice.config.FdsDbConfig;
import com.yokke.merchantservice.config.MerchantDbConfig;
import com.yokke.usermanagement.config.UserDbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.yokke")
@Import({MerchantDbConfig.class, UserDbConfig.class, FdsDbConfig.class})
@EnableScheduling

public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}