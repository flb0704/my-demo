package com.example.core.utils;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-7-3
 * @version 1.0
 */
public class ThreadPoolUtil {

    public static final ThreadPoolExecutor EXECUTOR_SERVICE;
    static {
        EXECUTOR_SERVICE = initTaskExecutor();
    }

    private static final String INFO_PATTERN = "CORE_SIZE[%s]  "
            + "ACTIVE_SIZE[%s] "
            + "COMPLETE_COUNT[%s] "
            + "POOL_SIZE[%s] "
            + "QUEUE_SIZE[%s] ";

    private static ThreadPoolExecutor initTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setTaskDecorator(new MdcDecorator());
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setKeepAliveSeconds(1);
        taskExecutor.setMaxPoolSize(6);
        taskExecutor.setThreadNamePrefix("Data-task-");
        taskExecutor.setQueueCapacity(30);

        taskExecutor.initialize();


        return taskExecutor.getThreadPoolExecutor();
    }



    private static class MdcDecorator implements TaskDecorator{

        @Override
        public Runnable decorate(Runnable runnable) {
            Map<String, String> contextMap = MDC.getCopyOfContextMap();
            return ()->{
                try {
                    MDC.setContextMap(contextMap);
                    printPoolInfo();
                    runnable.run();
                }
                finally {
                    printPoolInfo();
                    MDC.clear();
                }

            };
        }
    }

    private static void printPoolInfo(){
        LogUtil.info(String.format(INFO_PATTERN,
                EXECUTOR_SERVICE.getCorePoolSize(),
                EXECUTOR_SERVICE.getActiveCount(),
                EXECUTOR_SERVICE.getCompletedTaskCount(),
                EXECUTOR_SERVICE.getPoolSize(),
                EXECUTOR_SERVICE.getQueue().size()
                ));
    }


}
