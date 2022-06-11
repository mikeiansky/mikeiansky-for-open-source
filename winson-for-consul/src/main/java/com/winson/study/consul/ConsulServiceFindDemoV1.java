package com.winson.study.consul;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.agent.FullService;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.option.QueryOptions;

import java.security.Key;
import java.util.Map;

/**
 * @author winson
 * @date 2022/6/11
 **/
public class ConsulServiceFindDemoV1 {

    public static void main(String[] args) throws NotRegisteredException {

        Consul client = ConsulTemplate.getConsul();

        AgentClient agentClient = client.agentClient();

//        String serviceId = "winson-home-idel-client-001";
//        ConsulResponse<FullService> consulResponse = agentClient.getService(serviceId, QueryOptions.BLANK);
//        System.out.println(consulResponse);

        Map<String, Service> serviceMap = agentClient.getServices();
        for (String key : serviceMap.keySet()) {
//            System.out.println(key);
            Service service = serviceMap.get(key);
            System.out.println(service);
        }

    }

}
