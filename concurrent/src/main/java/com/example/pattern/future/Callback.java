package com.example.pattern.future;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
@FunctionalInterface
public interface Callback<T> {

    void call(T result);

}
