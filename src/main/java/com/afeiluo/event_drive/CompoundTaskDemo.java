package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 */


public class CompoundTaskDemo {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        TaskExecutor executor = manager.getExecutor();
        manager.start();//开一个线程循环去处理队列里面的任务
        TaskEventEmitter ioTask = TaskHelper.createIOTask(executor, "info.txt");
        TaskEventEmitter piTask = TaskHelper.createPiTask(executor, 10);
        final TaskEventEmitter guardTask = new GuardTask(manager, 2);
        EventHandler handler = new EventHandler() {
            @Override
            public void handle(EventObject event) {
                guardTask.emit("end");
            }
        };
        ioTask.on("close", handler);
        piTask.on("finish", handler);
        executor.submit(ioTask);
        executor.submit(piTask);
        executor.submit(guardTask);

    }
}
