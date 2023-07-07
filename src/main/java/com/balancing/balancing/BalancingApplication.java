package com.balancing.balancing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class BalancingApplication {
	public static void main(String[] args) {
		SpringApplication.run(BalancingApplication.class, args);
	}

//	@Bean
//	@Qualifier("balanced")
//	@LoadBalanced
//	public WebClient webClient() {
//		return WebClient.builder().build();
//	}
//
//	@Bean
//	@Qualifier("notbalanced")
//	public WebClient webClientNotBalanced() {
//		return WebClient.builder().build();
//	}

}
