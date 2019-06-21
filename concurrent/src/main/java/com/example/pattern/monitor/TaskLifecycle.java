package com.example.pattern.monitor;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public interface TaskLifecycle<T> {

    void onStart(Thread thread);

    void onRunning(Thread thread);

    void onFinish(Thread thread, T result);

    void onError(Thread thread, Exception e);

    /**
     * 一个空的实现类
     * @param <T>
     */
    class EmptLifecycle<T> implements TaskLifecycle<T> {

        @Override
        public void onStart(Thread thread) {
            System.out.println(thread.getName() + " on start ");
        }

        @Override
        public void onRunning(Thread thread) {
            System.out.println(thread.getName() + " onRunning ");
        }

        @Override
        public void onFinish(Thread thread, T result) {
            System.out.println(thread.getName() + " onFinish ");
        }

        @Override
        public void onError(Thread thread, Exception e) {
            System.out.println(thread.getName() + " onError " + e.getMessage());
        }
    }

}
