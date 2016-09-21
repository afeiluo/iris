package com.afeiluo.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ben on 16/9/19.
 */
public class ChannelCopy {
    private static final int BSIZE = 1024;


    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("arguments: sourcefile destfile");
            System.exit(1);
        }
        FileChannel in = new FileInputStream(args[0]).getChannel(),
                out = new FileOutputStream(args[1]).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while (in.read(buffer) != -1) {
            buffer.flip();//做好让别人从buffer中读取的准备
            out.write(buffer);
            buffer.clear();//做好让channel往里面写的准备
        }
        in.close();
        out.close();
    }
}
