package com.balancing.balancing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class BalancingApplication {
	public static void main(String[] args) {
		SpringApplication.run(BalancingApplication.class, args);
	}

//	@LoadBalanced
//	@Bean
//	WebClient.Builder webClientBuilder() {
//		return WebClient.builder();
//	}

}
