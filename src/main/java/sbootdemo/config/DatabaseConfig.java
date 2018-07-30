package sbootdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Steve
 */
@Slf4j
@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "sbootdemo.repos"/*,
//        entityManagerFactoryRef = "entityManager"*/)
@EnableConfigurationProperties({MyConfigProps.class,DataSourceProps.class})
public class DatabaseConfig {

    @Autowired
    MyConfigProps myConfigProps;
    @Autowired
    DataSourceProps dataSourceProps;
    @Autowired
    Environment env;

    @Bean
//    @ConfigurationProperties(prefix = "my.datasource") //Not work with DataSourceBuilder
    @Primary
    public DataSource getDataSource() {
//        Map<String, Object> props = new HashMap<>();
//        DriverManagerDataSource dataSource
//            = new DriverManagerDataSource(); //This one is not good based on https://stackoverflow.com/questions/28821521/configure-datasource-programmatically-in-spring-boot
        log.info("datasource cfg is {}", dataSourceProps);
//        return DataSourceBuilder.create().build();
        return DataSourceBuilder.create().url(dataSourceProps.getUrl()).username(dataSourceProps.getUsername()).driverClassName(dataSourceProps.getDriverClassName()).build();
    }

//    @Bean("entityManager")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactory =
//                new LocalContainerEntityManagerFactoryBean();
//
//        entityManagerFactory.setDataSource(dataSource());
//
//        // Classpath scanning of @Component, @Service, etc annotated class
//        entityManagerFactory.setPackagesToScan("sbootdemo.domain");
//
//        // Vendor adapter
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
//
//        // Hibernate properties
//        Properties additionalProperties = new Properties();
//        additionalProperties.put(
//                "hibernate.dialect",
//                myConfigProps.getHibernate().getDialect());
//        additionalProperties.put(
//                "hibernate.show_sql",
//                myConfigProps.getHibernate().getShowSql());
//        additionalProperties.put(
//                "hibernate.format_sql",
//                myConfigProps.getHibernate().getFormatSql());
//        additionalProperties.put(
//                "hibernate.hbm2ddl.auto",
//                myConfigProps.getHibernate().getHbm2ddlAuto());
//        entityManagerFactory.setJpaProperties(additionalProperties);
//
//        return entityManagerFactory;
//    }
}
