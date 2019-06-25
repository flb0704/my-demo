package com.example.pattern.active;

import com.example.pattern.future.Future;

import java.util.Map;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public abstract class MethodMessage {

    protected final Map<String,Object> params;

    protected final OrderService orderService;

    public MethodMessage(Map<String, Object> params, OrderService orderService) {
        this.params = params;
        this.orderService = orderService;
    }

    public abstract void execute();


    public static class FindOrderDetail extends MethodMessage {

        public FindOrderDetail(Map<String, Object> params, OrderService orderService) {
            super(params, orderService);
        }

        @Override
        public void execute() {
            Future<String> orderId = orderService.findOrderDetails((Long) params.get("orderId"));
            ActiveFuture<String> activeFuture = (ActiveFuture<String>) params.get("activeFuture");
            try {
                String result = orderId.get();
                activeFuture.finish(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
                activeFuture.finish(null);
            }
        }
    }

    public static class OrderMessage extends MethodMessage{

        public OrderMessage(Map<String, Object> params, OrderService orderService) {
            super(params, orderService);
        }

        @Override
        public void execute() {
            String account = (String) params.get("account");
            long id = (long) params.get("orderId");
            orderService.order(account, id);
        }
    }


}
