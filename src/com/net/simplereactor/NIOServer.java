package com.net.simplereactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        new Thread(new NIOServerRunner(9999)).start();
    }

    static class NIOServerRunner implements Runnable {

        private boolean running = true;
        private boolean hasWrite = false;

        private Selector selector;
        private ServerSocketChannel serverSocketChannel;

        NIOServerRunner(int port) throws IOException {
            // 1、打开选择器
            selector = Selector.open();

            // 2、打开ServerSocketChannel通道
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);// 设置为非阻塞

            // 3、绑定监听端口号
            serverSocketChannel.socket().bind(new InetSocketAddress(port));

            // 4、注册ServerSocketChannel通道准备接受客户端连接继续事件到选择器中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("NIOServerRunner初始化完成，准备接受客户端连接");
        }

        @Override
        public void run() {
            while (running) {
                try {
                    System.out.println("等待客户端接入");
                    // 5、轮询通道事件
                    selector.select();
                    // 6、获取事件SelectionKey的集合并进行处理
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isValid() && key.isAcceptable()) {
                            accept(key);
                        }

                        if (key.isValid() && key.isReadable()) {
                            read(key);
                            hasWrite = false;
                        }

                        if (key.isValid() && key.isWritable() && !hasWrite) {
                            write(key);
                            hasWrite = true;
                        }

                        // 13、移除事件，否则事件会一直存在
                        iterator.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void accept(SelectionKey key) throws IOException {
            ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();

            // 7、获取客户端连接
            SocketChannel socketChannel = serverSocketChannel1.accept();
            System.out.println("客户端[" + socketChannel.getRemoteAddress() + "]接入");
            socketChannel.configureBlocking(false);

            // 8、注册读事件到选择器上，等待客户端发送内容
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

        private void read(SelectionKey key) throws IOException {
            // 9、读取客户端发送内容
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(byteBuffer) > 0) {
                System.out.println(
                        "接收客户端[" + socketChannel.getRemoteAddress() + "]发送消息:" + new String(byteBuffer.array(),
                                StandardCharsets.UTF_8));
            }

            // 10、读完成，注册写事件到选择器
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        }

        private void write(SelectionKey key) throws IOException {
            // 11、响应客户端
            SocketChannel socketChannel = (SocketChannel) key.channel();
            socketChannel.write(ByteBuffer.wrap("你说啥，我看不懂".getBytes(StandardCharsets.UTF_8)));
            System.out.println("回复客户端[" + socketChannel.getRemoteAddress() + "]:你说啥，我看不懂");

            // 12、关闭相关资源
            socketChannel.close();
        }

    }

}
