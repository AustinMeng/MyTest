package com.net.basicreactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public final class Handler implements Runnable{
    final SocketChannel socketChannel;
    final SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    Handler(Selector sel, SocketChannel c) throws IOException {
        socketChannel = c;
        c.configureBlocking(false);
        sk = socketChannel.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    boolean inputIsComplete() {return false;}
    boolean outputIsComplete() {return false;}
    void process() {}
    @Override
    public void run() {
        try{
            if(state == READING) read();
            else if(state == SENDING) send();
        } catch (IOException e) {}
    }

    void read() throws IOException {
        socketChannel.read(input);
        if(inputIsComplete()) {
            process();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }
    void send() throws IOException {
        socketChannel.write(output);
        if(outputIsComplete()) sk.cancel();
    }
}
