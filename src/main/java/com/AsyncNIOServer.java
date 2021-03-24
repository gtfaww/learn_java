package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

public class AsyncNIOServer extends Thread {

    @Override
    public void run() {
        try (AsynchronousServerSocketChannel serverSock = AsynchronousServerSocketChannel.open();) {
            serverSock.bind(new InetSocketAddress(InetAddress.getLocalHost(), 8888));
            serverSock.accept(serverSock, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
                @Override
                public void completed(AsynchronousSocketChannel sockChannel, AsynchronousServerSocketChannel serverSock) {
                    serverSock.accept(serverSock, this);
                    sockChannel.write(Charset.defaultCharset().encode("hello"));
                }

                @Override
                public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AsyncNIOServer ms = new AsyncNIOServer();
        ms.start();
        sleep(1000);

        try (Socket socket = new Socket(InetAddress.getLocalHost(), 8888);) {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            br.lines().forEach(s -> System.out.println(s));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
