package com.afeiluo.nio.file;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ben on 16/9/14.
 */
public class FileReader {
    public static void main(String[] args) {
        try {
            RandomAccessFile afile = new RandomAccessFile("test.file", "rw");
            FileChannel inChannel = afile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);//read to buffer

            while (bytesRead != -1) {
                System.out.println("Read" + bytesRead);
                buf.flip();//make buffer ready to read
                while (buf.hasRemaining()) {
                    System.out.println((char) buf.get());//read 1 byte one time
                }
                buf.clear();//make buffer ready to write
                bytesRead = inChannel.read(buf);

            }
            afile.close();
        } catch (Exception e) {

        }
    }

}
