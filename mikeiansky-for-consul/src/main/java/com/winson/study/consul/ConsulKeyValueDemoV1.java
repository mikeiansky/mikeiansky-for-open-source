package com.winson.study.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;

/**
 * @author winson
 * @date 2022/6/11
 **/
public class ConsulKeyValueDemoV1 {

    public static void saveKeyValue(KeyValueClient keyValueClient) {
        keyValueClient.putValue("company", "ciwei");
        keyValueClient.putValue("school", "hunan_edu");
    }

    public static void queryKeyValue(KeyValueClient keyValueClient) {
        System.out.println("company : " + keyValueClient.getValueAsString("company").get());
        System.out.println("school : " + keyValueClient.getValueAsString("school").get());
    }

    public static void main(String[] args) {

        Consul consul = ConsulTemplate.getConsul();
        KeyValueClient keyValueClient = consul.keyValueClient();
//        saveKeyValue(keyValueClient);

        queryKeyValue(keyValueClient);

    }

}
