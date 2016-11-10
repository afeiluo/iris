package com.afeiluo.util;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;

public class FileUtilsTest {

    /**
     * The Windows separator character.
     * 
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    /**
     * The system separator character.
     */
    private static final char SYSTEM_SEPARATOR = File.separatorChar;

    /**
     * judge is current system windows
     * 
     * @return
     */
    static boolean isSystemWindows() {
        return SYSTEM_SEPARATOR == WINDOWS_SEPARATOR;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("D:/software/configs.json");
        File file1 = new File("D:/software/DropboxInstaller.exe");
        File copyFile = new File("D:/software/copy.json");
        File desFile = new File("D:/software/des.file");
        File desDir = new File("D:/software");
        byte[] bites = FileUtils.readFileToByteArray(file);
        System.out.println(new String(bites));
        System.out.println(FileUtils.byteCountToDisplaySize(2541784));
        System.out.println(FileUtils.byteCountToDisplaySize(2541784l));
        System.out.println(FileUtils.checksumCRC32(file));// 计算文件的crc32校验和
        System.out.println(FileUtils.contentEquals(file, file));// 检查两个文件是否一样
        System.out.println(FileUtils.contentEquals(file, file1));// 检查两个文件是否一样
        FileUtils.copyFile(file, copyFile);// 拷贝文件
        FileUtils.copyURLToFile(new URL("https://www.baidu.com/"), desFile, 3000, 3000);// 将URL上的资源copy到文件里面去
        FileUtils.forceDeleteOnExit(desFile);// jvm退出的时候删除文件
        if (isSystemWindows()) {
            System.out.println("windows");
        } else {
            System.out.println("other os");
        }
        LineIterator lineIter = FileUtils.lineIterator(desFile, "utf-8");// 对文件进行行的遍历
        while (lineIter.hasNext()) {
            System.out.println(lineIter.next());
        }
        // System.out.println(FileUtils.byteCountToDisplaySize(FileUtils.sizeOfDirectory(desDir)));// 文件夹的大小
        File[] files = new File[] { file, file1 };
        URL[] urls = FileUtils.toURLs(files);
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
            System.out.println(url.toString());
        }
        // FileUtils.waitFor(desFile, seconds);//等待desFile创建 seconds后超时
    }

}
