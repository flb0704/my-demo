package com.example.pattern.future;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: 提交任务时，创建新的线程来执行这个任务，进而达到异步执行的目的
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class FutureServiceImpl<T,R> implements FutureService<T,R> {


    private static final String PREFIX = "future-";

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    private String getNextName(){
        return PREFIX + atomicInteger.getAndIncrement();
    }


    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> future = new FutureTask<>();
        new Thread(()->{
            runnable.run();
            future.finish(null);
        },getNextName()).start();
        return future;
    }

    @Override
    public Future<R> submit(Task<T, R> task, T input) {
        final FutureTask<R> future = new FutureTask<>();
        new Thread(()->{
            R result = task.get(input);
            future.finish(result);
        },getNextName()).start();
        return future;
    }

    @Override
    public Future<R> submit(Task<T, R> task, T input, Callback<R> callback) {
        final FutureTask<R> future = new FutureTask<>();
        new Thread(()->{
            R result = task.get(input);
            future.finish(result);
            if (null != callback)
                callback.call(result);
        },getNextName()).start();
        return future;
    }


}
