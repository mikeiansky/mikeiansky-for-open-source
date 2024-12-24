package io.github.mikeiansky.zookeeper.sample;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author mike ian
 * @date 2024/12/24
 * @desc
 **/
public class ZookeeperSampleDemo {

    private static final String ZK_SERVER = "172.16.2.252:2181"; // ZooKeeper 服务地址
    private static final int SESSION_TIMEOUT = 3000; // 会话超时时间（毫秒）

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // 创建 ZooKeeper 客户端
        ZooKeeper zooKeeper = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("Event Received: " + event);
            }
        });

        // 创建节点
        String path = "/example_node";
        String data = "Hello ZooKeeper";
        if (zooKeeper.exists(path, false) == null) {
            zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("Node created: " + path);
        } else {
            System.out.println("Node already exists: " + path);
        }

        // 读取节点数据
        Stat stat = new Stat();
        byte[] nodeData = zooKeeper.getData(path, false, stat);
        System.out.println("Data of node " + path + ": " + new String(nodeData));

        // 更新节点数据
        String newData = "Updated Data";
        zooKeeper.setData(path, newData.getBytes(), stat.getVersion());
        System.out.println("Node data updated: " + path);

        // 再次读取节点数据
//        nodeData = zooKeeper.getData(path, false, stat);
        nodeData = zooKeeper.getData("/ian", false, stat);
        System.out.println("Data of node " + path + " after update: " + new String(nodeData));

        // 删除节点
        zooKeeper.delete(path, stat.getVersion());
        System.out.println("Node deleted: " + path);

        // 关闭连接
        zooKeeper.close();
    }

}
