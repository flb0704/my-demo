package com.example.pattern.monitor;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
@FunctionalInterface
public interface Task<T> {

    T call();

}
