package com.example.pattern.cache;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class LRUTest {


    public static void main(String[] args){
        LRUCache<String,String> cache = new LRUCache<>(4, String::toUpperCase);

        cache.put("a", "1");
        cache.put("ab", "1");
        cache.put("ac", "1");
        cache.put("ad", "1");

        System.out.println(cache.toString());
        cache.get("c");
        System.out.println(cache.toString());
        cache.get("ab");
        System.out.println(cache.toString());
    }

}
