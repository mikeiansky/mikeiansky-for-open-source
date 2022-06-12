package com.winson.study.consul;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author winson
 * @date 2022/6/12
 **/
public class AppService {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("192.168.204.1", 9004));

            for (;;){
                Socket client = serverSocket.accept();
                System.out.println("receive client : " + client);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
