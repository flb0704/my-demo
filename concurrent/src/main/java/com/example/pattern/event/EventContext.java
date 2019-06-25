package com.example.pattern.event;

import java.lang.reflect.Method;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public interface EventContext {

    String getSouce();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();

}
