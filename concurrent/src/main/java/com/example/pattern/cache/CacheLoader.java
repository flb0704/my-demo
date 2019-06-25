package com.example.pattern.cache;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
@FunctionalInterface
public interface CacheLoader<K,V> {

    V load(K k);

}
