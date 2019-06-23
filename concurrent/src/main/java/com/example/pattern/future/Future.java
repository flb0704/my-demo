package com.example.pattern.future;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public interface Future<T> {

    T get() throws InterruptedException;

    boolean isDone();

}
