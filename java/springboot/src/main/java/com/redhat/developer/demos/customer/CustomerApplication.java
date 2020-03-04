package com.redhat.developer.demos.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class CustomerApplication{

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
//    @Bean
//    public FilterRegistrationBean getPeticionFilter() {
//
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new RequestFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("requestFilter");
//
//        return registration;
//    }

}
