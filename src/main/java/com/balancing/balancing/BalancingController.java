package com.balancing.balancing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BalancingController {

  private final RestTemplate restTemplate;

  public BalancingController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/call-say-instance")
  public String callSayInstance() {
    return restTemplate.getForObject("http://localhost:8080/redireccion/say", String.class);
  }
}
