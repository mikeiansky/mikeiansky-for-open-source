package com.winson.study.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.StatusClient;
import jdk.jshell.Snippet;

/**
 * @author winson
 * @date 2022/6/11
 **/
public class ConsulStatusDemoV1 {

    public static void main(String[] args) {

        Consul consul = ConsulTemplate.getConsul();

        StatusClient statusClient = consul.statusClient();
        for (String peer : statusClient.getPeers()) {
            System.out.println("peer : " + peer);
        }


        System.out.println("leader : " + statusClient.getLeader());

    }

}
