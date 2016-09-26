package com.afeiluo.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ben on 16/9/19.
 */
public class GetChannel {
    public static final int BSIZE = 1024;

//看一下不同的地方
    public static void main(String[] args) {
        try {
            FileChannel fc = new FileOutputStream("data.txt").getChannel();
            fc.write(ByteBuffer.wrap("some texts".getBytes()));
            fc.close();
            fc = new RandomAccessFile("data.txt", "rw").getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap("some more".getBytes()));
            fc.close();
            fc = new FileInputStream("data.txt").getChannel();
            ByteBuffer buff = ByteBuffer.allocate(BSIZE);
            fc.read(buff);
            buff.flip();
            while (buff.hasRemaining()) {
                System.out.print((char) buff.get());
            }
            fc.close();
        } catch (Exception e) {
        }

    }
}
