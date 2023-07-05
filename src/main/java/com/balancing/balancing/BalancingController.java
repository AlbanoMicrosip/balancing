package com.balancing.balancing;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class BalancingController {

  private final RestTemplate restTemplate;
  private final DiscoveryClient discoveryClient;

  public BalancingController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
    this.restTemplate = restTemplate;
    this.discoveryClient = discoveryClient;
  }

  @GetMapping("/call-say-instance")
  public String callSayInstance() {
    List<InstanceInfo> instances = discoveryClient.getInstancesById("SAY-INSTANCE");
    if (instances != null && !instances.isEmpty()) {
      String uri = instances.get(0).getHomePageUrl();
      System.out.println("URL:::::::");
      System.out.println(uri);
      return restTemplate.getForObject(uri + "/", String.class);
    } else {
      return "No service instance available.";
    }
  }
}
