package com.example.pattern.event;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public interface Bus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object event, String topic);

    void close();

    String getBusName();

}
