//package com.github.yumyum.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//public class JdbcConfig {
////    @Bean
////    public DataSource dataSource(){
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setUsername("root");
////        dataSource.setPassword("8282");
////        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
////        dataSource.setUrl("jdbc:mysql://localhost:3306/yumyum?useUnicode=true&characterEncoding=UTF-8");
////        return dataSource;
////    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() { return new JdbcTemplate(dataSource()); }
//
//    @Bean(name = "tm1")
//    public PlatformTransactionManager transactionManager() { return new DataSourceTransactionManager(dataSource()); }
//
//}
