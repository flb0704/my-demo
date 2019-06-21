package com.example.pattern.readwrite;

/**
 * Description: 读写锁
 *
 * 有2个线程对同一个共享对象进行操作
 * 但都为 读-读时，不需要加锁
 * 但有存在一个写的时候，那么需要加锁
 *
 * 如果一个线程获取到了读锁，那么一定没有写锁，why？
 * 对象的读锁写锁是属于排它锁，同时只能有一个获取到
 *
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public interface ReadWriteLock {

    Lock readLock();

    Lock writeLock();

    int getWritingThread();

    int getWriteWaitting();

    int getReadingThread();


    static ReadWriteLock newInstance() {
        return new ReadWriteLockImpl();
    }

    static ReadWriteLock newInstance(boolean preferWrite){
        return new ReadWriteLockImpl(preferWrite);
    }




}
