package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NIOServer extends Thread{
    @Override
    public void run() {
        try(Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();) {
            serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(),8888));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()){
                    SelectionKey sk = it.next();
                    sayHello((ServerSocketChannel) sk.channel());
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sayHello(ServerSocketChannel channel) throws IOException {
        try(SocketChannel socketChannel = channel.accept();) {
            socketChannel.write(Charset.defaultCharset().encode("hello"));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NIOServer ms = new NIOServer();
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
