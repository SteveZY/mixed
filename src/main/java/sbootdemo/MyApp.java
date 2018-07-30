package sbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableAutoConfiguration
public class MyApp {
//    @Autowired
//    DataSource dataSource;
//    @Autowired
//    Environment env;
//    @Autowired
//    private EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyApp.class);
        app.run(args);
    }

//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "my.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
/*
    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);

        // Classpath scanning of @Component, @Service, etc annotated class
        entityManagerFactory.setPackagesToScan("sbootdemo.domain.*");

        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put(
                "hibernate.dialect",
                "org.hibernate.dialect.HSQLDialect");
        additionalProperties.put(
                "hibernate.show_sql",
                true);
        additionalProperties.put(
                "hibernate.hbm2ddl.auto",
                "create-drop");
        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }*/

    /**
     * Declare the transaction manager.
     */
   /* @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory);
        return transactionManager;
    }*/

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of
     * DataAccessException).
     */
   /* @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }*/
}
