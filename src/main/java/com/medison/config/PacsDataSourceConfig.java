package com.medison.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.medison.pacs",
        entityManagerFactoryRef = "pacsEntityManager",
        transactionManagerRef = "pacsTransactionManager"
)
@EnableTransactionManagement
class PacsDataSourceConfig {

    @Value("${spring.datasource.pacs.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.pacs.url}")
    private String url;
    @Value("${spring.datasource.pacs.username}")
    private String username;
    @Value("${spring.datasource.pacs.password}")
    private String password;

    @Bean(name = "pacsDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name = "pacsEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.medison.pacs");
        factory.setDataSource(dataSource());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); // DB에 맞는 dialect 설정
//        properties.put("hibernate.show_sql", "true"); // SQL 로그 표시
//        properties.put("hibernate.format_sql", "true"); // SQL 포맷팅
//        properties.put("hibernate.cache.use_second_level_cache", "true"); // 2차 캐시 사용
//        properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory"); // 캐시 팩토리 클래스 설정
//        properties.put("hibernate.cache.use_query_cache", "true"); // 쿼리 캐시 사용
//        properties.put("hibernate.jdbc.batch_size", "50"); // 배치 처리 사이즈
//        properties.put("hibernate.order_inserts", "true"); // Insert 정렬
//        properties.put("hibernate.order_updates", "true"); // Update 정렬
        factory.setJpaPropertyMap(properties);

        return factory;
    }

    @Bean(name = "pacsTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}