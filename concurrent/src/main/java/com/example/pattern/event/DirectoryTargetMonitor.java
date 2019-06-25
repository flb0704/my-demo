package com.example.pattern.event;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ThreadPoolExecutor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-25
 * @version 1.0
 */
public class DirectoryTargetMonitor {

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(EventBus eventBus, String path) {
        this(eventBus, path, "");
    }

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath,
                                  final String... morePaths){
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }

    public void startMonitor() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService, ENTRY_MODIFY, ENTRY_DELETE, ENTRY_CREATE);
        System.out.printf("the directory %s is monitoring... \n", path);

        this.start = true;
        while (start){
            WatchKey key = null;
            try {
                key = watchService.take();
                key.pollEvents().forEach(evt->{
                    WatchEvent.Kind<?> kind = evt.kind();
                    Path path = (Path) evt.context();
                    Path child = this.path.resolve(path);
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            } catch (InterruptedException e) {
                this.start = false;
            }
            finally {
                if (key != null){
                    key.reset();
                }
            }


        }
    }

    public void stopMonitor() throws IOException {
        System.out.println("begin stop");
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        System.out.println("finish stop");
    }


}
