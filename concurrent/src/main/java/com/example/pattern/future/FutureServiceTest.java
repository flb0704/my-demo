package com.example.pattern.future;

import java.util.concurrent.TimeUnit;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class FutureServiceTest {

    public static void main(String[] args) throws InterruptedException {
        FutureService<String, Integer> service = FutureService.newService();

        service.submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("i'm finished " + input);
            return input.length();
        }, "hello world", System.out::println);
    }


}
