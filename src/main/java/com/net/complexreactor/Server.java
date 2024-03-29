package com.net.complexreactor;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {


    private BlockingQueue<Call> queue = new LinkedBlockingQueue<Call>();

    private Queue<Call> responseCalls = new ConcurrentLinkedQueue<Call>();

    volatile boolean running = true;

    private Responder responder = null;

    private static int NIO_BUFFER_LIMIT = 64 * 1024;

    private int handler = 10;


    class Listener extends Thread {

        Selector selector;
        Reader[] readers;
        int robin;
        int readNum;

        Listener(int port) throws IOException {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port), 150);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            readNum = 10;
            readers = new Reader[readNum];
            for(int i = 0; i < readNum; i++) {
                readers[i] = new Reader(i);
                readers[i].start();
            }
        }


        public void run() {
            while(running) {
                try {
                    selector.select();
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while(it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if(key.isValid()) {
                            if(key.isAcceptable()) {
                                doAccept(key);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }

        public void doAccept(SelectionKey selectionKey) throws IOException {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel;
            while((socketChannel = serverSocketChannel.accept()) != null) {
                try {
                    socketChannel.configureBlocking(false);
                    socketChannel.socket().setTcpNoDelay(true);
                    socketChannel.socket().setKeepAlive(true);
                } catch (IOException e) {
                    socketChannel.close();
                    throw e;
                }
                Reader reader = getReader();
                try {
                    reader.startAdd();
                    SelectionKey readKey = reader.registerChannel(socketChannel);
                    Connection c = new Connection(socketChannel);
                    readKey.attach(c);
                } finally {
                    reader.finishAdd();
                }
            }
        }

        public Reader getReader() {
            if(robin == Integer.MAX_VALUE) {
                robin = 0;
            }
            return readers[(robin ++) % readNum];
        }
    }


    class Reader extends Thread {

        Selector readSelector;
        boolean adding;

        Reader(int i) throws IOException {
            setName("Reader-" + i);
            this.readSelector = Selector.open();
            System.out.println("Starting Reader-" + i + "...");
        }

        @Override
        public void run() {
            while(running) {
                try {
                    readSelector.select();
                    while(adding) {
                        synchronized(this) {
                            this.wait(1000);
                        }
                    }

                    Iterator<SelectionKey> it = readSelector.selectedKeys().iterator();
                    while(it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if(key.isValid()) {
                            if(key.isReadable()) {
                                doRead(key);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }

        public void doRead(SelectionKey selectionKey) {
            Connection c = (Connection) selectionKey.attachment();
            if(c == null) {
                return;
            }

            int n;
            try {
                n = c.readAndProcess();
            } catch (IOException e) {
                System.err.println(e);
                n = -1;
            } catch (Exception e) {
                System.err.println(e);
                n = -1;
            }
            if(n == -1) {
                c.close();
            }
        }

        public SelectionKey registerChannel(SocketChannel channel) throws IOException {
            return channel.register(readSelector, SelectionKey.OP_READ);
        }

        public void startAdd() {
            adding = true;
            readSelector.wakeup();
        }

        public synchronized void finishAdd() {
            adding = false;
            this.notify();
        }
    }


    class Connection {
        private SocketChannel channel;
        private ByteBuffer dataBufferLength;
        private ByteBuffer dataBuffer;
        private boolean skipHeader;

        public Connection(SocketChannel channel) {
            this.channel = channel;
            this.dataBufferLength = ByteBuffer.allocate(4);
        }

        public int readAndProcess() throws IOException {
            int count;
            if(!skipHeader) {
                count = channelRead(channel, dataBufferLength);
                if (count < 0 || dataBufferLength.remaining() > 0) {
                    return count;
                }
            }

            skipHeader = true;

            if(dataBuffer == null) {
                dataBufferLength.flip();
                int dataLength = dataBufferLength.getInt();
                dataBuffer = ByteBuffer.allocate(dataLength);
            }

            count = channelRead(channel, dataBuffer);

            if(count >= 0 && dataBuffer.remaining() == 0) {
                process();
            }

            return count;
        }


        /**
         * process the dataBuffer
         */
        public void process() {
            dataBuffer.flip();
            byte[] data = dataBuffer.array();
            Call call = new Call(this, data, responder);
            try {
                queue.put(call);
            } catch (InterruptedException e) {
                System.err.println(e);
            }

        }


        public void close() {
            if(channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
        }
    }


    class Responder extends Thread {

        Selector writeSelector;

        public Responder() throws IOException {
            writeSelector = Selector.open();
        }

        public void run() {
            while(running) {
                try {
                    registWriters();
                    int n = writeSelector.select(1000);
                    if(n == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> it = writeSelector.selectedKeys().iterator();
                    while(it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if(key.isValid() && key.isWritable()) {
                            doAsyncWrite(key);
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }


        public void registWriters() throws IOException {
            Iterator<Call> it = responseCalls.iterator();
            while(it.hasNext()) {
                Call call = it.next();
                it.remove();
                SelectionKey key = call.conn.channel.keyFor(writeSelector);
                try {
                    if (key == null) {
                        try {
                            call.conn.channel.register(writeSelector, SelectionKey.OP_WRITE, call);
                        } catch (ClosedChannelException e) {
                            //the client went away
                            System.out.println("the client went away");
                            e.printStackTrace();
                        }
                    } else {
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                } catch (CancelledKeyException e) {
                    System.out.println("the client went away");
                    e.printStackTrace();
                }
            }
        }


        public void registerForWrite(Call call) throws IOException {
            responseCalls.add(call);
            writeSelector.wakeup();
        }

        private void doAsyncWrite(SelectionKey key) throws IOException {
            Call call = (Call) key.attachment();
            if(call.conn.channel != key.channel()) {
                throw new IOException("bad channel");
            }
            int numBytes = channelWrite(call.conn.channel, call.response);
            if(numBytes < 0 || call.response.remaining() == 0) {
                try {
                    key.interestOps(0);
                } catch (CancelledKeyException e) {
                    System.out.println("Exception while changing ops : " + e);

                }
            }
        }

        private void doResponse(Call call) throws IOException {
            //if data not fully send, then register the channel for async writer
            if(!processResponse(call)) {
                registerForWrite(call);
            }
        }

        private boolean processResponse(Call call) throws IOException {
            boolean error = true;
            try {
                int numBytes = channelWrite(call.conn.channel, call.response);
                if (numBytes < 0) {
                    throw new IOException("error socket write");
                }
                error = false;
            } finally {
                if(error) {
                    call.conn.close();
                }
            }
            if(!call.response.hasRemaining()) {
                call.done = true;
                return true;
            }
            return false;
        }
    }

    class Handler extends Thread {

        public Handler(int i) {
            setName("handler-" + i);
            System.out.println("Starting Handler-" + i + "...");
        }

        public void run() {
            while(running) {
                try {
                    Call call = queue.take();
                    process(call);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void process(Call call) throws IOException {
            byte[] request = call.request;
            String message = new String(request);
            System.out.println("received mseesage: " + message);

            //each channel write 2MB data for test
            int dataLength = 2 * 1024 * 1024;
            ByteBuffer buffer = ByteBuffer.allocate(4 + dataLength);

            buffer.putInt(dataLength);
            writeDataForTest(buffer);
            buffer.flip();

            call.response = buffer;
            responder.doResponse(call);
        }
    }

    public void writeDataForTest(ByteBuffer buffer) {
        int n = buffer.limit() - 4;
        for(int i = 0; i < n; i++) {
            buffer.put((byte)0);
        }
    }


    class Call {
        Connection conn;
        byte[] request;
        Responder responder;
        ByteBuffer response;
        boolean done;
        public Call(Connection conn, byte[] request, Responder responder) {
            this.conn = conn;
            this.request = request;
            this.responder = responder;
        }
    }


    public int channelRead(ReadableByteChannel channel, ByteBuffer buffer) throws IOException {
        return buffer.remaining() <= NIO_BUFFER_LIMIT ? channel.read(buffer) : channleIO(channel, null, buffer);
    }

    public int channelWrite(WritableByteChannel channel, ByteBuffer buffer) throws IOException {
        return buffer.remaining() <= NIO_BUFFER_LIMIT ? channel.write(buffer) : channleIO(null, channel, buffer);
    }


    public int channleIO(ReadableByteChannel readCh, WritableByteChannel writeCh, ByteBuffer buffer) throws IOException {
        int initRemaining = buffer.remaining();
        int originalLimit = buffer.limit();

        int ret = 0;
        try {
            while (buffer.remaining() > 0) {
                int ioSize = Math.min(buffer.remaining(), NIO_BUFFER_LIMIT);
                buffer.limit(buffer.position() + ioSize);
                ret = readCh == null ? writeCh.write(buffer) : readCh.read(buffer);
                if (ret < ioSize) {
                    break;
                }
            }
        } finally {
            buffer.limit(originalLimit);
        }

        int byteRead = initRemaining - buffer.remaining();
        return byteRead > 0 ? byteRead : ret;
    }


    public void startHandler() {
        for(int i = 0; i < handler; i++) {
            new Handler(i).start();
        }
    }


    public void start() throws IOException {
        new Listener(10000).start();
        responder = new Responder();
        responder.start();
        startHandler();
        System.out.println("server startup! ");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }
}
