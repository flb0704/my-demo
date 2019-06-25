package com.example.pattern.event;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class SimpleSubscriber {

    @Subscribe
    public void method(String message){
        System.out.println("== simple ==" + message);
    }

    @Subscribe(topic = "test")
    public void method2(String message){
        System.out.println(">>>> simple22 <<<<" + message);
    }

}
