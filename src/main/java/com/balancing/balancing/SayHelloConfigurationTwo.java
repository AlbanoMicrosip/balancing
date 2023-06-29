package com.balancing.balancing;


import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import com.balancing.balancing.util.DNSUtil;

import java.net.InetAddress;
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
    try {

      List<InetAddress> hosts = DNSUtil.resolve(serviceId);
      System.out.println("Despues de resolver");
      for (InetAddress inet:hosts) {
        System.out.println(inet.toString());
      }

      List<ServiceInstance> servers = hosts.stream().map(
        host -> {
          return new DefaultServiceInstance(serviceId+Math.random()*100, serviceId, host.getHostAddress(), port, false);
        }
      ).collect(Collectors.toList());
//      servers.add(new DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8090, false));
      return Flux.just(servers);
    } catch (Exception e) {
      return Flux.error(e);
    }
  }
  @Override
  public Flux<List<ServiceInstance>> get(Request request) {
    return get();
  }

}
