package com.net.simplereactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        NIOClientRunner runner = new NIOClientRunner("127.0.0.1", 9999);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入发送内容");
        runner.sendMsg(scanner.nextLine());
        runner.handler();
    }

    static class NIOClientRunner {

        private volatile boolean running = true;

        private Selector selector;
        private SocketChannel socketChannel;

        NIOClientRunner(String ip, int port) throws IOException {
            // 1、打开选择器
            selector = Selector.open();

            // 2、打开SocketChannel通道
            socketChannel = SocketChannel.open();

            // 3、连接指定IP，端口服务器
            socketChannel.connect(new InetSocketAddress(ip, port));
            socketChannel.configureBlocking(false);// 设置为非阻塞

            // 4、注册选择器事件
            socketChannel.register(selector, SelectionKey.OP_READ);
        }


        void handler() throws IOException {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    // 6、接收消息
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    while (socketChannel.read(byteBuffer) > 0) {
                        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
                    }
                }
            }
        }

        void sendMsg(String msg) throws IOException {
            if ("exit".equals(msg)) {
                socketChannel.close();
                socketChannel = null;
                running = false;
                return;
            }
            // 5、发送消息
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        }
    }
}
