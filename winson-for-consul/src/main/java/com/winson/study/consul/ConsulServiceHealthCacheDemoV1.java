package com.winson.study.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.health.HealthCheck;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.option.ConsistencyMode;
import com.orbitz.consul.option.ImmutableEventOptions;
import com.orbitz.consul.option.ImmutableQueryOptions;
import com.orbitz.consul.option.QueryOptions;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.LockSupport;

/**
 * @author winson
 * @date 2022/6/11
 **/
public class ConsulServiceHealthCacheDemoV1 {

    public static void main(String[] args) {



        Consul consul = ConsulTemplate.getConsul();

//        QueryOptions queryOptions = ImmutableQueryOptions.builder()
//                .consistencyMode(
//                        ConsistencyMode.CONSISTENT)
////                .index(BigInteger.ZERO)
////                .index(BigInteger.ZERO)
//                .build();

        HealthClient healthClient = consul.healthClient();
        String serviceName = "winson-home-idel-client";
//        String serviceName = "winson-home-app-dev";
//        ServiceHealthCache serviceHealthCache = ServiceHealthCache.newCache(healthClient, serviceName,
//                false, queryOptions, 5);

        ServiceHealthCache serviceHealthCache = ServiceHealthCache.newCache(healthClient, serviceName,
                false, QueryOptions.BLANK, 5);
        // Consul cluster has no elected leader
        // knownLeader

        serviceHealthCache.addListener(new ConsulCache.Listener<ServiceHealthKey, ServiceHealth>() {
            @Override
            public void notify(Map<ServiceHealthKey, ServiceHealth> newValues) {
                System.out.println("service health notify start ... ");
//                System.out.println(newValues);
                for (ServiceHealthKey serviceHealthKey : newValues.keySet()) {
                    ServiceHealth serviceHealth = newValues.get(serviceHealthKey);
                    System.out.println("service : " + serviceHealth.getService().getService()
                            + " , id : " + serviceHealth.getService().getId()
                            + " , status : " + serviceHealth.getChecks().get(1).getStatus());
                    for (HealthCheck check : serviceHealth.getChecks()) {
                        System.out.println("check : " + check.getCheckId() + " , check status : " + check.getStatus());
                    }
                }
                System.out.println("service health notify complete ... ");
            }
        });

        serviceHealthCache.start();

        LockSupport.park();

        serviceHealthCache.stop();

        System.out.println("service health cache complete ... ");

    }

}
