package com.example.pattern.event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
class Registry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> container = new ConcurrentHashMap<>();

    public void bind(Object obj){
        List<Method> subscribeMethod = getSubscribeMethod(obj);
        subscribeMethod.forEach(m -> tierSubscriber(obj, m));

    }

    public void unbind(Object obj) {
        container.forEach((key, queue) -> {
            queue.forEach(s -> {
                if (s.getSubscribeObject() == obj) {
                    s.setDisable(true);
                }
            });
        });
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubsciber(final String key){
        return container.get(key);
    }

    private void tierSubscriber(Object obj, Method method){
        // 获取方法上的注解
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        // 当没有这个topic时，创建
        container.computeIfAbsent(topic, key-> new ConcurrentLinkedQueue<>());
        // 注册进去
        container.get(topic).add(new Subscriber(obj, method));
    }

    private List<Method> getSubscribeMethod(Object object){
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = object.getClass();

        while (temp != null){
            Method[] declaredMethods = temp.getDeclaredMethods();
            Arrays.stream(declaredMethods).filter(m-> m.isAnnotationPresent(Subscribe.class)
                    && m.getParameterCount() == 1
                    && m.getModifiers() == Modifier.PUBLIC).forEach(methods::add);
            temp = temp.getSuperclass();
        }

        return methods;
    }


}
