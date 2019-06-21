package com.example.pattern.monitor;

import java.util.concurrent.TimeUnit;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public class ObservableTest {


    public static void main(String[] args){

        Observable observable = new ObservableThread<Void>(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("finish done...");
            return null;
        });

        observable.start();


    }

}
