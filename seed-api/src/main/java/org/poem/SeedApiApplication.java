package org.poem;

import feign.Request;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class SeedApiApplication {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @LoadBalanced
    @Bean
    @SentinelRestTemplate
    public RestTemplate restLbTemplate() {
        return new RestTemplate();
    }


    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(6000,6000);
    }

    public static void main(String[] args) {
        SpringApplication.run(SeedApiApplication.class, args);
    }
}
