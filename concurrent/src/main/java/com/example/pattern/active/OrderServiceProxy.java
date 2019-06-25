package com.example.pattern.active;

import com.example.pattern.future.Future;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 静态代理
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class OrderServiceProxy implements OrderService{

    private OrderService orderService;

    private ActiveMessageQueue queue;

    public OrderServiceProxy(OrderService orderService, ActiveMessageQueue queue) {
        this.orderService = orderService;
        this.queue = queue;
    }

    @Override
    public Future<String> findOrderDetails(long orderId) {
        ActiveFuture<String> activeFuture = new ActiveFuture<>();
        Map<String,Object> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("activeFuture", activeFuture);

        MethodMessage message = new MethodMessage.FindOrderDetail(params, orderService);
        queue.offer(message);
        return activeFuture;
    }

    @Override
    public void order(String account, long orderId) {
        Map<String,Object> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("account", account);
        MethodMessage message = new MethodMessage.OrderMessage(params, orderService);
        queue.offer(message);
    }
}
