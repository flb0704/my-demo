package com.example.pattern.worker;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class WorkerTest {


    public static void main(String[] args){
        // 一条流水线上有4个工人
        final ProductionChannel channel = new ProductionChannel(4);
        AtomicInteger atomicInteger = new AtomicInteger(1);
        // 产品的速率为6
        IntStream.range(0,6).forEach(i->{
            new Thread(()->{
                while (true){
                    channel.offerProduction(new Production(atomicInteger.getAndIncrement()));
                    try {
                        TimeUnit.SECONDS.sleep(2L);
                    }
                    catch (InterruptedException e){
                        // ignore
                    }

                }
            }).start();
        });


    }

}
