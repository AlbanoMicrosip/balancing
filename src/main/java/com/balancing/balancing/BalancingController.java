package com.balancing.balancing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class BalancingController {

  private final RestTemplate restTemplate;

  private final RestTemplate restTemplateNotBalanced;

  @Autowired
  public BalancingController(@Qualifier("balanced") RestTemplate restTemplate, @Qualifier("notbalanced") RestTemplate restTemplateNotBalanced) {
    this.restTemplate = restTemplate;
    this.restTemplateNotBalanced = restTemplateNotBalanced;
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
