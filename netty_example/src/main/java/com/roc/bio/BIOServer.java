package com.roc.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("started");
        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println("连接到一个客户端");
            executorService.execute(() -> {
                handle(accept);
            });

        }
    }
    //编写一个handle方法,和客户端通讯
    public  static  void handle(Socket socket){
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read();
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    System.out.println("链接断开");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
