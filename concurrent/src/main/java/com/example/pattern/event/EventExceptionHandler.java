package com.example.pattern.event;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public interface EventExceptionHandler {

    void handler(Exception e, EventContext context);

}
