package com.example.pattern.active;

import com.example.pattern.future.Future;
import com.example.pattern.future.FutureService;

import java.util.concurrent.TimeUnit;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService{

    @Override
    public Future<String> findOrderDetails(long orderId) {
        return FutureService.<Long,String>newService().submit(input -> "the order detail is " + orderId + "_no",orderId,null);
    }

    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("process account " + account + ", orderId " + orderId);
    }
}
