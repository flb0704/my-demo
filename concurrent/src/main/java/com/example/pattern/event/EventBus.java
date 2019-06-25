package com.example.pattern.event;

import java.util.concurrent.Executor;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class EventBus implements Bus {

    private String busName;

    private static final String DEFAULT_NAME = "default-bus";

    private static final String DEFAULT_TOPIC_NAME = "my-topic";

    // 注册表，用于维护subscriber
    private final Registry registry = new Registry();

    private final Dispatcher dispatcher;

    public EventBus(){
        this(DEFAULT_NAME, null, Dispatcher.SEQ_THREAD);
    }

    public EventBus(String busName){
        this(busName, null, Dispatcher.SEQ_THREAD);
    }

    public EventBus(EventExceptionHandler handler){
        this(DEFAULT_NAME, handler, Dispatcher.SEQ_THREAD);
    }


    EventBus(String busName, EventExceptionHandler handler, Executor executor) {
        this.busName = busName;
        this.dispatcher = Dispatcher.newDispatcher(handler, executor);
    }


    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event,DEFAULT_TOPIC_NAME);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
