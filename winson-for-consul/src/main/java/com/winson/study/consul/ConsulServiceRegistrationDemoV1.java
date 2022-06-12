package com.winson.study.consul;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.NotRegisteredException;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * @author winson
 * @date 2022/6/11
 * @desc consul 客户端
 **/
public class ConsulServiceRegistrationDemoV1 {

    public static void main(String[] args) throws NotRegisteredException {

        System.out.println("hello consul!");
        Consul consul = ConsulTemplate.getConsul();

        AgentClient agentClient = consul.agentClient();
        HashMap<String,String> meta = new HashMap<>();
        meta.put("age", "32");
        meta.put("address", "shenzhen-001");
        String serviceId = "winson-home-idel-client-003";
        Registration service = ImmutableRegistration.builder()
                .id(serviceId)
                .name("winson-home-idel-client")
                .address("192.168.204.1")
                .port(9004)
                .tags(Arrays.asList("winson-home-idel"))
                .meta(meta)
                .check(Registration.RegCheck.ttl(10L))
                .build();

        agentClient.register(service);


        // keep alive
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){
                    try {
                        agentClient.pass(serviceId);
                    } catch (NotRegisteredException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();


        agentClient.pass(serviceId);

        System.out.println("consul client demo complete ... ");
        LockSupport.park();

    }

}
