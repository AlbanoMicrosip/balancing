package com.balancing.balancing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BalancingApplication {
	public static void main(String[] args) {
		SpringApplication.run(BalancingApplication.class, args);
	}

	@Bean
	public ReactorLoadBalancerExchangeFilterFunction lbFunction(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
		return lbFunction;
	}

	@Bean
	@Qualifier("balanced")
	public WebClient webClient(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
		return WebClient.builder()
			.filter(lbFunction)
			.build();
	}

	@Bean
	@Qualifier("notbalanced")
	public WebClient webClientNotBalanced() {
		return WebClient.builder().build();
	}

}
