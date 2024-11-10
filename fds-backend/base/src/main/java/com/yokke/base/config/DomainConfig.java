package com.yokke.base.config;

//import com.yokke.base.security.AuditorAwareImpl;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.auditing.DateTimeProvider;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.time.OffsetDateTime;
//import java.util.Optional;

//@Configuration
//@EntityScan("com.yokke")
//@EnableJpaRepositories("com.yokke")
//@EnableTransactionManagement
//@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
//public class DomainConfig {
//
////    @Bean(name = "auditingDateTimeProvider")
////    public DateTimeProvider dateTimeProvider() {
////        return () -> Optional.of(OffsetDateTime.now());
////    }
////
////    @Bean(name = "auditorProvider")
////    public AuditorAware<String> auditorProvider() {
////        return new AuditorAwareImpl();
////    }
//
//}

import com.yokke.base.security.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class DomainConfig {
    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

    @Bean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
