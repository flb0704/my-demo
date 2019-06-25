package com.example.pattern.active;

import com.example.pattern.future.Future;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public interface OrderService {

    // 根据订单号获取订单的细节
    Future<String> findOrderDetails(long orderId);

    // 提交订单，没有返回值
    void order(String account, long orderId);

}
