package com.example.pattern.future;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public interface FutureService<T,R>  {

    // 提交不需要返回值得任务
    Future<?> submit(Runnable runnable);

    // 提交需要返回值得结果
    Future<R> submit(Task<T, R> task, T input);

    Future<R> submit(Task<T, R> task, T input, Callback<R> callback);

    static <T,R> FutureService<T,R> newService(){
        return new FutureServiceImpl<>();
    }

}
