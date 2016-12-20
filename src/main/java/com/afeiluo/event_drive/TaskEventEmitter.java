package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract public class TaskEventEmitter implements Task, EventEmitter {
    private Map<String, List<EventHandler>> eventHandlerMap = new HashMap<String, List<EventHandler>>();
    final private TaskExecutor executor;

    public TaskEventEmitter(TaskExecutor executor) {
        this.executor = executor;
        addDefaultExceptionHandlers();
    }

    protected void addDefaultExceptionHandlers() {
        EventHandler eh = new EventHandler() {
            @Override
            public void handle(EventObject event) {
                ((Exception) event.getSource()).printStackTrace();
            }
        };
        on(Exception.class.getName(), eh);
        on(IOException.class.getName(), eh);
        on(RuntimeException.class.getName(), eh);
        on(NullPointerException.class.getName(), eh);
        on(IndexOutOfBoundsException.class.getName(), eh);
    }

    @Override
    public void on(String eventName, EventHandler handler) {
        if (!eventHandlerMap.containsKey(eventName)) {
            List<EventHandler> eventHandlerList = new LinkedList<EventHandler>();
            eventHandlerMap.put(eventName, eventHandlerList);
        }
        eventHandlerMap.get(eventName).add(handler);
    }

    @Override
    public void remove(String eventName) {
        eventHandlerMap.remove(eventName);
    }

    @Override
    public void emit(final String eventName, final Object... args) {
        if (eventHandlerMap.containsKey(eventName)) {
            List<EventHandler> eventHandlerList = eventHandlerMap.get(eventName);
            for (final EventHandler handler : eventHandlerList) {
                executor.submit(new Task() {// 放一个任务到阻塞队列中去
                    @Override
                    public void execute() {
                        handler.handle(new EventObject(eventName, TaskEventEmitter.this, args));// 任务的内容
                    }
                });
            }
        } else {
            System.out.println("No event handler listen this event: " + eventName);
            if (args[0] instanceof Exception) {
                ((Exception) args[0]).printStackTrace();
            }
        }
    }

    @Override
    public void execute() {
        try {
            run();
        } catch (Exception e) {
            emit(e.getClass().getName(), e);
        }
    }

    abstract protected void run() throws Exception;
}
