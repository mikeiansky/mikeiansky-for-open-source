package com.winson.study.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.option.QueryOptions;

import java.util.List;

/**
 * @author winson
 * @date 2022/6/11
 **/
public class ConsulHealthDemoV1 {

    public static void main(String[] args) {

        Consul consul = ConsulTemplate.getConsul();
        HealthClient healthClient = consul.healthClient();

        String serviceId = "winson-home-app-001";
        String service = "winson-home-app-dev";

        System.out.println(healthClient.getHealthyServiceInstances(service, QueryOptions.BLANK));


        // Discover only "passing" nodes
        ConsulResponse<List<ServiceHealth>> response = healthClient.getAllServiceInstances(serviceId);
        System.out.println(response.getResponse().size());
        for (ServiceHealth serviceHealth : response.getResponse()) {
            System.out.println(serviceHealth);
        }

        response = healthClient.getHealthyServiceInstances(serviceId);
        System.out.println(response.getResponse().size());

    }

}
