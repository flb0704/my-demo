package com.example.pattern.cache;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class LRUCache<K,V> {

    private final LinkedList<K> keys = new LinkedList<>();

    private final HashMap<K,V> map = new HashMap<>();

    private final int capacity;

    private final CacheLoader<K,V> loader;

    public LRUCache(int capacity, CacheLoader<K, V> loader) {
        this.capacity = capacity;
        this.loader = loader;
    }

    public void put(K k, V val) {
        if (keys.size() >= capacity) {
            K key = keys.removeFirst();
            map.remove(key);
        }
        if (keys.contains(k)) {
            keys.remove(k);
        }
        keys.addLast(k);
        map.put(k, val);
    }

    public V get(K k){
        V value;
        boolean success = keys.remove(k);
        if (!success){
            value = loader.load(k);
            this.put(k,value);
        }
        else {
            value = map.get(k);
            keys.addLast(k);
        }
        return value;
    }


    @Override
    public String toString() {
        return "LRUCache{" +
                "keys=" + keys +
                '}';
    }
}
