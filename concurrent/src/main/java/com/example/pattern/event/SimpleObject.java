package com.example.pattern.event;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class SimpleObject {


    @Subscribe(topic = "test-topic")
    public void test2(int a){
        System.out.println("my int " + a);
    }

}
