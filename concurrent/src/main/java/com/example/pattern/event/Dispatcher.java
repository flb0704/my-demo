package com.example.pattern.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class Dispatcher {


    private final Executor executor;

    private final EventExceptionHandler exceptionHandler;


    static final Executor SEQ_THREAD = SeqExecutorService.INSTANCE;

    static final Executor PER_THREAD = PreThreadExecutorService.INSTANCE;

    public Dispatcher(Executor executor, EventExceptionHandler exceptionHandler) {
        this.executor = executor;
        this.exceptionHandler = exceptionHandler;
    }


    public void dispatch(Bus bus, Registry registry, Object event, String topic) {

        ConcurrentLinkedQueue<Subscriber> subscribers = registry.scanSubsciber(topic);
        if (null == subscribers) {
            if (exceptionHandler != null) {
                this.exceptionHandler.handler(new IllegalArgumentException(),
                        new BaseEventContext(bus.getBusName(), null, event));
            }
            return;
        }

        subscribers.stream().filter(s -> !s.isDisable()).filter(s -> {
            Method subscribe = s.getSubscribeMethod();
            Class<?> aclass = subscribe.getParameterTypes()[0];
            return aclass.isAssignableFrom(event.getClass());
        }).forEach(s -> realInvokeSubscibe(s, event, bus));
    }

    private void realInvokeSubscibe(Subscriber subscriber, Object event, Bus bus) {
        Method method = subscriber.getSubscribeMethod();
        Object obj = subscriber.getSubscribeObject();

        this.executor.execute(()->{

            try {
                method.invoke(obj,event);

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                if (null != this.exceptionHandler){
                    this.exceptionHandler.handler(e, new BaseEventContext(bus.getBusName(), subscriber, event));
                }
            }

        });

    }

    public void close(){
        if (executor instanceof ExecutorService){
            ((ExecutorService) executor).shutdown();
        }
    }


    static Dispatcher newDispatcher(EventExceptionHandler handler, Executor executor){
        return new Dispatcher(executor, handler);
    }

    static Dispatcher seqDispatcher(EventExceptionHandler handler){
        return new Dispatcher(SEQ_THREAD, handler);
    }

    static Dispatcher perDispatcher(EventExceptionHandler handler){
        return new Dispatcher(PER_THREAD, handler);
    }



    // 顺序执行的ExecutorService
    private static class SeqExecutorService implements Executor{

        private final static SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }





    // 每个线程负责一次消息推送
    private static class PreThreadExecutorService implements Executor{

        private final static PreThreadExecutorService INSTANCE = new PreThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }





    private static class BaseEventContext implements EventContext{

        private final String eventBusName;
        private final Subscriber subscriber;
        private final Object event;

        public BaseEventContext(String eventBusName, Subscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSouce() {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber == null?  null : subscriber.getSubscribeObject();
        }

        @Override
        public Method getSubscribe() {
            return subscriber == null?  null : subscriber.getSubscribeMethod();
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }


}
