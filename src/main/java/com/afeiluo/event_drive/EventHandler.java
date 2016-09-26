package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 * 处理具体事件的回调接口
 */
public interface EventHandler {
    public void handle(EventObject event);
}
