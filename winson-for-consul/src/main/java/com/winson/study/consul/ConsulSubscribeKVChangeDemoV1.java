package com.winson.study.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.cache.KVCache;
import com.orbitz.consul.model.kv.Value;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * @author winson
 * @date 2022/6/11
 **/
public class ConsulSubscribeKVChangeDemoV1 {

    public static void main(String[] args) {

        Consul consul = ConsulTemplate.getConsul();

        KeyValueClient keyValueClient = consul.keyValueClient();
        keyValueClient.getNetworkTimeoutConfig();

        KVCache kvCache = KVCache.newCache(keyValueClient, "company", 5);
        kvCache.addListener(new ConsulCache.Listener<String, Value>() {
            @Override
            public void notify(Map<String, Value> newValues) {
//                System.out.println("company newValues : " + newValues);
                for (String key : newValues.keySet()) {
                    System.out.println(key + " : " + newValues.get(key).getValueAsString().get());
                }
            }
        });

        kvCache.start();

        LockSupport.park();

        kvCache.stop();
        System.out.println("kv listener demo complete ... ");
    }

}
