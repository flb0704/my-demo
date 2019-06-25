package com.example.pattern.event;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class AsyncEventBus extends EventBus {

    AsyncEventBus(String busName, EventExceptionHandler handler, ThreadPoolExecutor executor) {
        super(busName,handler,executor);
    }

    public AsyncEventBus(String busName, ThreadPoolExecutor executor) {
        this(busName, null, executor);
    }

    public AsyncEventBus(ThreadPoolExecutor executor){
        this("default-async",null,executor);
    }

    public AsyncEventBus(EventExceptionHandler handler, ThreadPoolExecutor executor){
        this("default-async", handler, executor);
    }

}
