package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MySocket extends Thread {

    private ServerSocket ss;

    private Executor executor;

    public int getPort() {
        return ss.getLocalPort();
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(0);
            executor = Executors.newFixedThreadPool(8);
            while (true) {
                Socket s = ss.accept();
                RequestHandler ph = new RequestHandler(s);
                executor.execute(ph);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MySocket ms = new MySocket();
        ms.start();

        try (Socket socket = new Socket(InetAddress.getLocalHost(), ms.getPort());) {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            br.lines().forEach(s -> System.out.println(s));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class RequestHandler extends Thread {
    private Socket socket;

    RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
            pw.println("hello world");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
