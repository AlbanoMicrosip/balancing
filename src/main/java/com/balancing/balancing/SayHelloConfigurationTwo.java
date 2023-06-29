package com.balancing.balancing;


import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import com.balancing.balancing.util.DNSUtil;

import java.net.InetAddress;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class SayHelloConfigurationTwo {
  @Bean
  ServiceInstanceListSupplier serviceInstanceListSupplier() {
    return new DnsServiceInstanceListSupplier("say-hello", 8090);
  }
}

class DnsServiceInstanceListSupplier implements ServiceInstanceListSupplier {

  private final String serviceId;
  private final int port;

  public DnsServiceInstanceListSupplier(String serviceId, int port) {
    this.serviceId = serviceId;
    this.port = port;
  }

  @Override
  public String getServiceId() {
    return serviceId;
  }

  @Override
  public Flux<List<ServiceInstance>> get() {
    System.out.println("Entrando a Get");
    return Flux.interval(Duration.ofSeconds(5))  // emits every 5 seconds
      .onBackpressureDrop()  // drops emissions if the downstream can't keep up
      .flatMap(tick -> {
        try {
          System.out.println("Dentro de Try");
          List<InetAddress> hosts = DNSUtil.resolve(serviceId);
          List<ServiceInstance> servers = hosts.stream().map(
            host -> new DefaultServiceInstance(serviceId + Math.random() * 100, serviceId, host.getHostAddress(), port, false)
          ).collect(Collectors.toList());
          return Flux.just(servers);
        } catch (Exception e) {
          return Flux.error(e);
        }
      });
  }
}
