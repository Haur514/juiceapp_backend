package com.example.demo.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entity.HistoryEntity;

@Configuration
public class HistoryConfiguration {
    @Bean 
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public HistoryEntity historyEntity(){
        return new HistoryEntity();
    }

}
