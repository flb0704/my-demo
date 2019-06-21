package com.example.pattern.readwrite;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public interface Lock {


    void lock() throws InterruptedException;

    void unlock();

}
