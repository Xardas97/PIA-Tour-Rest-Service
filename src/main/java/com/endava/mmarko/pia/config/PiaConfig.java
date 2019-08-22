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
    public DataSource dataSource(){
       DriverManagerDataSource dataSource =  new DriverManagerDataSource();
       dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
       dataSource.setUrl("jdbc:mysql://localhost:3306/bgwalkingtours?zeroDateTimeBehavior=convertToNull");
       dataSource.setUsername("root");
       dataSource.setPassword("root");
       return dataSource;
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

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan("com.endava.mmarko.pia.models");
        return emfb;
   }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


}
