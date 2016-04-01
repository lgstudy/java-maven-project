package com.opensourceteams.modules.common.gramar.NIO操作.n_06_nio_socket_通信.n_06_01_nio_socket_通信_客户端发送服务端接收;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 日期: 2016-03-31  11:34
 * 开发人:刘文  -->  (372065525@qq.com)
 * 功能描述:
 */
public class Client {

    public static void main(String[] args) throws Exception{



        int port = 9999;
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8888));

        //sc.bind(new InetSocketAddress(port));
        sc.configureBlocking(false);
        ByteBuffer buf = ByteBuffer.wrap("你好".getBytes());
        sc.write(buf);
        System.out.println("over");

        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       // Selector selector = Selector.open();
       // sc.register(selector,SelectionKey.OP_CONNECT);


    }
}
