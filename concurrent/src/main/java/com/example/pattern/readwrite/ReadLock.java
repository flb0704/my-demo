package com.example.pattern.readwrite;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public class ReadLock implements Lock{

    private final ReadWriteLockImpl readWriteLock;

    public ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (this.readWriteLock.getMUTEX()){
            // 当执行写锁的线程不为0，或者 设置为了偏向写锁，而且有等待写锁的，那么空转
            while (readWriteLock.getWritingThread() > 0 ||
                    (readWriteLock.getPreferWriter() && readWriteLock.getWriteWaitting() > 0)
            ) {
                readWriteLock.getMUTEX().wait();
            }
            readWriteLock.incrementReadThread();
        }
    }

    @Override
    public void unlock() {
        // 释放锁的过程使得当前reading的数量减1
        synchronized (this.readWriteLock.getMUTEX()){
            readWriteLock.decrementReadThread();
            // 改为偏向写锁，使得写锁更加有机会得到执行
            readWriteLock.changePreferWriter(true);
            System.out.println("readlock unlock");
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
