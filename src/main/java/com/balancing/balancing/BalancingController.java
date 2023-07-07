package com.balancing.balancing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class BalancingController {

  @LoadBalanced
  private final WebClient.Builder webClientBuilder;

  @Autowired
  public BalancingController(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  @GetMapping("/loadbalanced")
  public Mono<String> getLoadBalanced() {
    return webClientBuilder.build().get()
      .uri("http://SAY-INSTANCE/")
      .retrieve()
      .bodyToMono(String.class);
  }
}
