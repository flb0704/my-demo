package com.example.pattern.active;

import java.util.LinkedList;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class ActiveMessageQueue {


    private final LinkedList<MethodMessage> list = new LinkedList<>();

    public ActiveMessageQueue() {
        new ActiveDaemonThread(this).start();
    }

    public synchronized void offer(MethodMessage message){
        list.addLast(message);
        this.notify();
    }

    public synchronized MethodMessage take(){
        while(list.isEmpty()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list.removeFirst();
    }
}
