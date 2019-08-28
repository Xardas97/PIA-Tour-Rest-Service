package com.endava.mmarko.pia.config;

import com.endava.mmarko.pia.aspects.Logging;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.endava.mmarko.pia.config.DataSourceConfigParams.DataSourceType;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.endava.mmarko.pia.repositories")
@EnableAspectJAutoProxy
@ComponentScan(basePackages={"com.endava.mmarko.pia"},
        excludeFilters={ @Filter(type= FilterType.ANNOTATION, value= EnableWebMvc.class) })
public class PiaConfig {
    @Bean
    public Logging logging(){
        return new Logging();
    }

    @Bean
    public BeanPostProcessor persistenceTranslator(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DataSource dataSource(DataSourceConfigParams configParams){
        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
        dataSource.setDriverClassName(configParams.driver);
        dataSource.setUrl(configParams.url);
        dataSource.setUsername(configParams.username);
        dataSource.setPassword(configParams.password);
        return dataSource;
    }

    @Bean
    @Profile("dev")
    DataSourceConfigParams mysqlDataSourceConfigParams(){
        return new DataSourceConfigParams(DataSourceType.MySQL);
    }

    @Bean
    @Profile("prod")
    DataSourceConfigParams sqlServerDataSourceConfigParams(){
        return new DataSourceConfigParams(DataSourceType.SQLServer);
    }

   @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
       HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
       adapter.setDatabase(Database.MYSQL);
       adapter.setShowSql(false);
       adapter.setGenerateDdl(false);
       adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
       return adapter;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter){

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("com.endava.mmarko.pia.models");
        return emf;
   }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


}
