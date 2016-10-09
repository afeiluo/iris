package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 */

import static com.afeiluo.event_drive.EventConstant.*;

public class CompoundTaskDemo {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        TaskExecutor executor = manager.getExecutor();
        manager.start();//开一个线程循环去处理队列里面的任务
        TaskEventEmitter ioTask = TaskHelper.createIOTask(executor, "info.txt");
        TaskEventEmitter piTask = TaskHelper.createPiTask(executor, 10);
        final TaskEventEmitter guardTask = new GuardTask(manager, 2);//确保ioTask和piTask的finish都执行完了之后才关闭manager
        EventHandler handler = new EventHandler() {
            @Override
            public void handle(EventObject event) {
                guardTask.emit(END);
            }
        };
        ioTask.on(CLOSE, handler);
        piTask.on(FINISH, handler);
        executor.submit(ioTask);
        executor.submit(piTask);
        executor.submit(guardTask);

    }
}
