package com.winson.study.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.option.QueryOptions;

import java.util.Map;
import java.util.concurrent.locks.LockSupport;

/**
 * @author winson
 * @date 2022/6/11
 **/
public class ConsulServiceHealthCacheDemoV1 {

    public static void main(String[] args) {

        Consul consul = ConsulTemplate.getConsul();
        HealthClient healthClient = consul.healthClient();
        String serviceName = "winson-home-idel-client";
        ServiceHealthCache serviceHealthCache = ServiceHealthCache.newCache(healthClient, serviceName,
                false, QueryOptions.BLANK, 5);
        serviceHealthCache.addListener(new ConsulCache.Listener<ServiceHealthKey, ServiceHealth>() {
            @Override
            public void notify(Map<ServiceHealthKey, ServiceHealth> newValues) {
                System.out.println(newValues);
            }
        });

        serviceHealthCache.start();

        LockSupport.park();

        serviceHealthCache.stop();

        System.out.println("service health cache complete ... ");

    }

}
