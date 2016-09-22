package com.afeiluo.nio.file;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * Created by ben on 16/9/21.
 * 在操作系统层面对文件加锁
 */
public class FileBlocking {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("file.txt");
        FileLock fl = fos.getChannel().tryLock();
        if (fl != null) {
            System.out.println("locked File");
            TimeUnit.MILLISECONDS.sleep(100);
            fl.release();
            System.out.println("Release Lock");
        }
        fos.close();

    }
}
