package com.balancing.balancing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class BalancingController {

  private final WebClient.Builder webClientBuilder;

  private final WebClient.Builder webClientBuilderNotBalanced;

  @Autowired
  public BalancingController(@Qualifier("balanced") WebClient.Builder webClientBuilder, @Qualifier("notBalanced") WebClient.Builder webClientBuilderNotBalanced) {
    this.webClientBuilder = webClientBuilder;
    this.webClientBuilderNotBalanced = webClientBuilderNotBalanced;
  }

  @GetMapping("/loadbalanced")
  public Mono<String> getLoadBalanced() {
    return webClientBuilder.build().get()
      .uri("http://SAY-INSTANCE/")
      .retrieve()
      .bodyToMono(String.class);
  }

  @GetMapping("/notloadbalanced")
  public Mono<String> getNotLoadBalanced() {
    return webClientBuilderNotBalanced.build().get()
      .uri("http://SAY-INSTANCE/")
      .retrieve()
      .bodyToMono(String.class);
  }
}
