package com.example.pattern.worker;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class Worker extends Thread{

    private String name;
    private ProductionChannel channel;

    private Random random = new Random();

    public Worker(String name, ProductionChannel channel) {
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true){
            int i = random.nextInt(2);
            Production production = channel.takeProduction();
            System.out.println(this.name + "begin process production");
            production.create();
            try {
                TimeUnit.SECONDS.sleep(i);
            } catch (InterruptedException e) {
                // ignore
            }
            System.out.println(this.name + "finish.. process production");
        }
    }
}
