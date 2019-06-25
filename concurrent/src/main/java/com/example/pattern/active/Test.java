package com.example.pattern.active;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class Test {

    private final static ActiveMessageQueue queue = new ActiveMessageQueue();

    static OrderService toActiveObject(OrderService orderService){
        return new OrderServiceProxy(orderService,queue);
    }

    public static void main(String[] args) throws InterruptedException {
        OrderService service = toActiveObject(new OrderServiceImpl());

        service.order("hello",123);
        System.out.println("return immediately");
        System.out.println(service.findOrderDetails(123).get());
        Thread.currentThread().join();
    }

}
