package com.winson.study.consul;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author winson
 * @date 2022/6/12
 **/
public class AppClient {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket();

        socket.connect(new InetSocketAddress("192.168.204.1", 9004));

    }
}
