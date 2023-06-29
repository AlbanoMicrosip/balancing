package com.balancing.balancing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.*;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Microsip Platform as a Service
 * Copyright Aplicaciones y Proyectos Computacionales, S. A. de C. V. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by paco <paco@technogi.com.mx> on 15/06/18.
 */
public class DNSUtil {

  private static final Logger log = LoggerFactory.getLogger(DNSUtil.class);

  public static List<InetAddress> resolve(String host) {
    try {
//      Lookup.getDefaultCache(DClass.IN).setMaxCache(30);
//      Lookup.getDefaultCache(DClass.IN).setMaxNCache(30);
      Lookup lookup = new Lookup(host);
      Record[] result = lookup.run();

      if (result == null || lookup.getResult() != Lookup.SUCCESSFUL) {
        System.out.println("result is " + result);
        log.debug("result is " + result);
        log.debug("lookup.getResult() is " + lookup.getResult());
        System.out.println("lookup.getResult() is " + lookup.getResult());

        // Reseteamos el caché para evitar error cuando se reinicia el servicio al que se está intentando resolver
        Cache  defaultCache = Lookup.getDefaultCache(DClass.IN);
        defaultCache.clearCache();

        return Collections.emptyList();
      }

      List<InetAddress> hosts = Arrays.asList(result).stream()
        .map(record -> {
          InetAddress hostAdress = ((ARecord) record).getAddress();
          return hostAdress;
        }).collect(Collectors.toList());
      return hosts;
    } catch (Exception ex) {
      return null;
    }
  }
}
