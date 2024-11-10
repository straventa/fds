package com.yokke.merchantservice.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.yokke.merchantservice",
        entityManagerFactoryRef = "merchantEntityManager",
        transactionManagerRef = "merchantTransactionManager"
)

@EnableTransactionManagement
public class MerchantDbConfig {
    @Autowired
    private Environment env;

//    public PersistenceProductAutoConfiguration() {
//        super();
//    }

    //
    @Bean
    public LocalContainerEntityManagerFactoryBean merchantEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(merchantDataSource());
        em.setPackagesToScan("com.yokke.merchantservice");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource.merchant.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.datasource.merchant.jpa.database-platform"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.merchant")
    public DataSource merchantDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        // Log the properties for debugging
        System.out.println("User DataSource: " + dataSource);
        System.out.println("User DataSource: " + dataSource);
        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setJdbcUrl(env.getProperty("spring.datasource.merchant.url"));
        dataSource2.setUsername(env.getProperty("spring.datasource.merchant.username"));
        dataSource2.setPassword(env.getProperty("spring.datasource.merchant.password"));
        dataSource2.setDriverClassName(env.getProperty("spring.datasource.merchant.driver-class-name"));
        return dataSource2;

    }

    @Bean
    public PlatformTransactionManager merchantTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(merchantEntityManager().getObject());
        return transactionManager;
    }
}