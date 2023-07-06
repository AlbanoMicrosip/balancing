package com.balancing.balancing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class BalancingController {

  @LoadBalanced
  private final RestTemplate restTemplate;

  @Autowired
  public BalancingController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/call-say-instance")
  public String callSayInstance() {
      return restTemplate.getForObject("http://SAY-INSTANCE" + "/", String.class);
  }

  @GetMapping("/call-say-instance-fix")
  public String callSayInstanceFix() {
      return restTemplate.getForObject("http://say-hello1:8080/", String.class);
  }
}
