package org.poem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Administrator
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SeedAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeedAuthApplication.class, args);
    }
}
