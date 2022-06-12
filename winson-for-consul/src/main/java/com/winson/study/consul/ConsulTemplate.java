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
        return getConsul("192.168.204.130", 8500);
    }

    public static Consul getConsul(String host, int port) {
        return Consul.builder()
                .withHostAndPort(HostAndPort.fromHost("192.168.204.130").withDefaultPort(8500))
                .withReadTimeoutMillis(20000)
                .build(); // connect on localhost
    }

}
