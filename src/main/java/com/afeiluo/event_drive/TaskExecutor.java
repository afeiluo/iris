package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 */



/**
 * 任务执行器
 */
public interface TaskExecutor extends Task {

    /**
     * 提交一个任务
     *
     * @param task
     */
    public void submit(Task task);
}
