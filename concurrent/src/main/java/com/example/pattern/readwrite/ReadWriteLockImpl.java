package com.example.pattern.readwrite;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public class ReadWriteLockImpl implements ReadWriteLock {

    private volatile boolean preferWriter;

    private final Object MUTEX = new Object();

    private AtomicInteger writingThread = new AtomicInteger(0);

    private AtomicInteger writeWriter = new AtomicInteger(0);

    private AtomicInteger readingThread = new AtomicInteger(0);


    public ReadWriteLockImpl(){
        this(true);
        // 这个是不推荐的做法，这里仅仅是为了监听内部的变化
        new Thread(()->{
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(" \t\t\t\t\t\t\t\t\t\t\t statistic ->  " +
                        "writingThread " + this.writingThread.get() +
                        " wait " + this.writeWriter.get()
                        + " readingThread " + this.readingThread.get());
            }
        }).start();
    }

    public ReadWriteLockImpl(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    @Override
    public Lock readLock() {
        return new ReadLock(this);
    }

    @Override
    public Lock writeLock() {
        return new WriterLock(this);
    }


    void incrementWritingThread(){
        this.writingThread.incrementAndGet();
    }

    void decreamentWritingThread(){
        this.writingThread.decrementAndGet();
    }


    void incrementReadThread(){
        this.readingThread.incrementAndGet();
    }

    void decrementReadThread(){
        this.readingThread.decrementAndGet();
    }

    void incrementWaiter(){
        this.writeWriter.incrementAndGet();
    }

    void decrementWaiter(){
        this.writeWriter.decrementAndGet();
    }


    @Override
    public int getWritingThread() {
        return writingThread.get();
    }

    @Override
    public int getReadingThread() {
        return this.readingThread.get();
    }

    @Override
    public int getWriteWaitting() {
        return writeWriter.get();
    }

    Object getMUTEX(){
        return this.MUTEX;
    }

    void changePreferWriter(boolean preferWriter){
        this.preferWriter = preferWriter;
    }

    boolean getPreferWriter(){
        return this.preferWriter;
    }



}
