package com.afeiluo.nio.file;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by ben on 16/9/21.
 */
public class UsingBuffers {
    private static void symmetricScramble(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.println("start position:" + buffer.position() + " limit:" + buffer.limit() + " capacity" + buffer.capacity());
            buffer.mark();
            char c1 = buffer.get();//基数位的字符
            char c2 = buffer.get();//偶数位的字符
            buffer.reset();//从新从mark的位置(基数位)开始写入
            buffer.put(c2).put(c1);//基数位放c2偶数位放c1
            System.out.println("end position:" + buffer.position() + " limit:" + buffer.limit() + " capacity" + buffer.capacity());
        }
    }

    public static void main(String[] args) {
        char[] data = "UsingBuffers".toCharArray();
        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);//24
        CharBuffer cb = bb.asCharBuffer();// 24/2
        cb.put(data);
        System.out.println(cb.rewind());//可以读从0到上次写的最后一个位置的数据
        symmetricScramble(cb);
        System.out.println(cb.rewind());
        symmetricScramble(cb);
        System.out.println(cb.rewind());
    }
}
