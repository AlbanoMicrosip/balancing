package com.balancing.balancing;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public BalancingController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
    this.restTemplate = restTemplate;
    this.discoveryClient = discoveryClient;
  }

  @GetMapping("/call-say-instance")
  public String callSayInstance() {
    List<ServiceInstance> instances = discoveryClient.getInstances("SAY-INSTANCE");
    if (instances != null && !instances.isEmpty()) {
      URI uri = instances.get(0).getUri();
      System.out.println("URL:::::::");
      System.out.println(uri.toString());
      return restTemplate.getForObject(uri + "/say", String.class);
    } else {
      return "No service instance available.";
    }
  }
}
