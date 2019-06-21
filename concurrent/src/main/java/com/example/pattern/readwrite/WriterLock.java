package com.example.pattern.readwrite;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public class WriterLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    public WriterLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMUTEX()){
            boolean waitlock = true;
            // 如果有获取到写锁的线程，或者有其他等待写锁的线程，那么进行等待
            while (readWriteLock.getReadingThread() > 0 || readWriteLock.getWritingThread() > 0 ) {
                if (waitlock) {
                    readWriteLock.incrementWaiter();
                    waitlock = false;
                }

                readWriteLock.getMUTEX().wait();
            }
            // 获得了执行权，那么等待数-1
            if (!waitlock){
                readWriteLock.decrementWaiter();
            }

            // 成功获取到写锁，写锁的线程++
            this.readWriteLock.incrementWritingThread();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMUTEX()){
            readWriteLock.decreamentWritingThread();
            // 改为false，便于其他读锁获取到
            readWriteLock.changePreferWriter(false);
            System.out.println("writelock unlock");
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
