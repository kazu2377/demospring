package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.example.demo.model.DonutDAO;

@Configuration
@PropertySource (value = "classpath:application.properties")
public class AppConfig {
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer () {
        return new PropertySourcesPlaceholderConfigurer ();
    }
    
    @Bean
    public DonutDAO donutDAO () {
        return new DonutDAO ();
    }
    
}