package com.balancing.balancing;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
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

  private static final Logger log = LoggerFactory.getLogger(DnsServiceInstanceListSupplier.class);

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
    return Flux.interval(Duration.ofSeconds(30))
      .onBackpressureLatest()
      .map(tick -> {
        try {
          List<InetAddress> hosts = DNSUtil.resolve(serviceId);
          System.out.println("Despues de resolver");
          for (InetAddress inet : hosts) {
            System.out.println(inet.toString());
          }

          List<ServiceInstance> servers = hosts.stream().map(
            host -> {
              return new DefaultServiceInstance(serviceId + host.hashCode(), serviceId, host.getHostAddress(), port, false);
            }
          ).collect(Collectors.toList());
          System.out.println("servers is " + servers);
          return servers;

        } catch (Exception e) {
          throw new RuntimeException("Error updating server list", e);
        }
      })
      .onErrorResume(e -> Flux.empty()) // Si hay una excepción, emitir un Flux vacío en lugar de propagar el error
      .share();
  }

}
