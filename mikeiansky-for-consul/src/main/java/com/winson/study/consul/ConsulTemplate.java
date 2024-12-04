package com.winson.study.consul;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;

/**
 * @author winson
 * @date 2022/6/11
 * @desc consul 模板
 **/
public class ConsulTemplate {

    public static Consul getConsul() {
        return getConsul("172.16.2.211", 8500);
    }

    public static Consul getConsul(String host, int port) {
        return Consul.builder()
                .withHostAndPort(HostAndPort.fromHost(host).withDefaultPort(port))
                .withReadTimeoutMillis(20000)
                .build(); // connect on localhost
    }

}
