package com.example.pattern.event;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class FileChangeListener {

    @Subscribe
    public void onChange(FileChangeEvent event){
        System.out.printf("%s-%s\n", event.getPath(), event.getKind());
    }



    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        final EventBus bus = new AsyncEventBus(executorService);
        bus.register(new FileChangeListener());
        new DirectoryTargetMonitor(bus,"D:\\demo").startMonitor();
    }

}
