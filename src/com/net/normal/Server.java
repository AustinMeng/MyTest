package com.net.normal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {


    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(0);
            while (!Thread.interrupted()) {
                new Thread(new Handler(ss.accept())).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class Handler implements Runnable {

        final Socket socket;

        Handler(Socket s) {
            socket = s;
        }

        @Override
        public void run() {
            try {
                byte[] input = new byte[1024];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException e) {

            }
        }

        private byte[] process(byte[] cmd) {
            return null;
        }
    }
}
