package com.yokke.usermanagement.config;

import com.yokke.base.security.AuditorAwareImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Optional;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.yokke.usermanagement" ,
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)

@EnableTransactionManagement
public class UserDbConfig {
    @Autowired
    private Environment env;

//    public PersistenceProductAutoConfiguration() {
//        super();
//    }

    //

    @Bean
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource());
        em.setPackagesToScan("com.yokke.usermanagement");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource.user.ddl-auto"));
        //set postgres dialect
        properties.put("hibernate.dialect",env.getProperty("spring.datasource.user.jpa.database-platform"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.user")
    public DataSource userDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        // Log the properties for debugging
        System.out.println("User DataSource: " + dataSource);
        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setJdbcUrl(env.getProperty("spring.datasource.user.url"));
        dataSource2.setUsername(env.getProperty("spring.datasource.user.username"));
        dataSource2.setPassword(env.getProperty("spring.datasource.user.password"));
        dataSource2.setDriverClassName(env.getProperty("spring.datasource.user.driver-class-name"));
        return dataSource2;    }

    @Bean
    public PlatformTransactionManager userTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(userEntityManager().getObject());
        return transactionManager;
    }
}