package com.example.pattern.active;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class ActiveDaemonThread extends Thread {

    private final ActiveMessageQueue queue;

    public ActiveDaemonThread(ActiveMessageQueue queue) {
        super("active-thread");
        this.queue = queue;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true){
            MethodMessage methodMessage = queue.take();
            methodMessage.execute();
        }
    }
}
