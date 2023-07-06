package com.balancing.balancing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class BalancingController {

  private final WebClient webClient;

  private final WebClient webClientNotBalanced;

  @Autowired
  public BalancingController(@Qualifier("balanced") WebClient webClient, @Qualifier("notbalanced") WebClient webClientNotBalanced) {
    this.webClient = webClient;
    this.webClientNotBalanced = webClientNotBalanced;
  }

  @GetMapping("/call-say-instance")
  public Mono<String> callSayInstance() {
    return webClient.get().uri("http://SAY-INSTANCE").retrieve().bodyToMono(String.class);
  }

  @GetMapping("/call-say-instance-fix")
  public Mono<String> callSayInstanceFix() {
    return webClientNotBalanced.get().uri("http://say-hello1:8080/").retrieve().bodyToMono(String.class);
  }
}