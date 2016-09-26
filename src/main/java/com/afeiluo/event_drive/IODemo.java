package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 */

import java.io.*;


public class IODemo {
    public static void main(String[] args) throws InterruptedException {
        String fileName = "info.txt";
        final TaskManager manager = new TaskManager();
        manager.start();
        TaskExecutor executor = manager.getExecutor();
        Task ioTask = TaskHelper.createIOTask(executor, fileName);
        executor.submit(ioTask);
        Thread.sleep(5000L);
        manager.stop();
    }
}

class IOTask extends TaskEventEmitter {
    final private String fileName;
    final private String encoding;

    public IOTask(TaskExecutor executor, String fileName, String encoding) {
        super(executor);
        this.fileName = fileName;
        this.encoding = encoding;
    }

    public String getFileName() {
        return fileName;
    }

    public String getEncoding() {
        return encoding;
    }

    @Override
    protected void run() throws Exception {
        InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);//rescource目录下

        if (fis != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, encoding));
            emit("open", getFileName());
            emit("next", reader);
        } else {
            throw new FileNotFoundException(fileName);
        }
    }
}
