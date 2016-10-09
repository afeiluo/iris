package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.afeiluo.event_drive.EventConstant.*;


public class TaskHelper {

    public static TaskEventEmitter createIOTask(TaskExecutor executor, String fileName) {

        final IOTask task = new IOTask(executor, fileName, "UTF-8");
        //注册打开文件的事件
        task.on(OPEN, new EventHandler() {
            @Override
            public void handle(EventObject event) {
                String fileName = (String) event.getArgs()[0];
                System.out.println(Thread.currentThread() + " - " + fileName + " has been opened.");
            }
        });
        //注册读取下一行的事件
        task.on(NEXT, new EventHandler() {
            @Override
            public void handle(EventObject event) {
                BufferedReader reader = (BufferedReader) event.getArgs()[0];
                try {
                    String line = reader.readLine();
                    if (line != null) {
                        task.emit(READY, line);//通知文件已经就绪
                        task.emit(NEXT, reader);//通知可以读取下一行
                    } else {
                        task.emit(CLOSE, task.getFileName());//通知文件已经读取完了需要关闭了
                    }
                } catch (IOException e) {
                    task.emit(e.getClass().getName(), e, task.getFileName());//通知异常处理
                    try {
                        reader.close();
                        task.emit(CLOSE, task.getFileName());//在发生异常的时候同时关闭文件
                    } catch (IOException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        });
        task.on(READY, new EventHandler() {//注册文件准备就绪事件
            @Override
            public void handle(EventObject event) {
                String line = (String) event.getArgs()[0];
                int len = line.length();
                int wordCount = line.split("[\\s+,.]+").length;
                System.out.println(Thread.currentThread() + " - word count: " + wordCount + " length: " + len);
            }
        });
        task.on(IOException.class.getName(), new EventHandler() {//注册IO异常事件
            @Override
            public void handle(EventObject event) {
                Object[] args = event.getArgs();
                IOException e = (IOException) args[0];
                String fileName = (String) args[1];
                System.out.println(Thread.currentThread() + " - An IOException occurred while reading " + fileName + ", error: " + e.getMessage());
            }
        });
        task.on(CLOSE, new EventHandler() {//注册关闭文件的事件
            @Override
            public void handle(EventObject event) {
                String fileName = (String) event.getArgs()[0];
                System.out.println(Thread.currentThread() + " - " + fileName + " has been closed.");
            }
        });
        task.on(FileNotFoundException.class.getName(), new EventHandler() {//注册文件不存在的事件
            @Override
            public void handle(EventObject event) {
                FileNotFoundException e = (FileNotFoundException) event.getArgs()[0];
                e.printStackTrace();
                System.exit(1);
            }
        });
        return task;
    }

    public static TaskEventEmitter createPiTask(TaskExecutor executor, int n) {
        final PICalcTask task = new PICalcTask(executor, n);
        // 计算下一个级数项
        task.on(NEXT, new EventHandler() {
            @Override
            public void handle(EventObject event) {
                int n = ((Integer) event.getArgs()[0]).intValue();
                double xn = Math.pow(-1, n - 1) / (2 * n - 1);
                task.emit(SUM, xn);
            }
        });
        // 将每一个级数项加起来
        task.on(SUM, new EventHandler() {
            private int i = 0;
            private double sum = 0;

            @Override
            public void handle(EventObject event) {
                double xn = ((Double) event.getArgs()[0]).doubleValue();
                sum += xn;
                i++;
                System.out.println(Thread.currentThread() + " - sum = " + sum);
                if (i >= task.getN()) {
                    task.emit(FINISH, sum * 4);
                } else {
                    task.emit(NEXT, i + 1);
                }
            }
        });
        // 完成PI的近似计算
        task.on(FINISH, new EventHandler() {
            @Override
            public void handle(EventObject event) {
                Double sum = (Double) event.getArgs()[0];
                System.out.println("pi=" + sum);
            }
        });
        return task;
    }
}
