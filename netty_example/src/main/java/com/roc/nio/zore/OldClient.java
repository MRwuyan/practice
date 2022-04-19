package com.roc.nio.zore;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class OldClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",7001);
        String filename = "";
        FileInputStream fileInputStream = new FileInputStream(filename);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[5000];
        long readCount=0;
        long total=0;
        long startTime = System.currentTimeMillis();
        while ((readCount=fileInputStream.read(bytes))>=0) {
            total+=readCount;
            dataOutputStream.write(bytes);
        }
        System.out.println("发送总字节数:"+total+",耗时"+(System.currentTimeMillis()-startTime));
        dataOutputStream.close();
        socket.close();
        fileInputStream.close();
    }
}
