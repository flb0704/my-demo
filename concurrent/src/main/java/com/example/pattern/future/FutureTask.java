package com.example.pattern.future;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class FutureTask<T> implements Future<T> {

    private T result;

    private boolean isDone;

    private final Object MUTEX = new Object();


    @Override
    public T get() throws InterruptedException {
        synchronized (MUTEX){
            while (!isDone){
                MUTEX.wait();
            }
            return result;
        }
    }

    protected void finish(T result){
        synchronized (MUTEX){
            if (isDone){
                return;
            }
            this.result = result;
            this.isDone = true;
            MUTEX.notifyAll();
        }
    }


    @Override
    public boolean isDone() {
        return isDone;
    }
}
