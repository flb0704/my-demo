package com.example.pattern.event;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class TestA {

    public static void main(String[] args){

        Bus bus = new EventBus("TestBug");
        bus.register(new SimpleSubscriber());
        bus.post("hello");
        bus.post("hello-v","test");
        bus.post("tttttt-v","my-topic");
    }




}
