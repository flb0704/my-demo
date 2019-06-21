package com.example.pattern.readwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description: 共享数据
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public class ShareData {

    private final List<Character> clist = new ArrayList<>(10);

    private final ReadWriteLock readWriterLock = ReadWriteLock.newInstance();

    private final Lock readLock = readWriterLock.readLock();

    private final Lock writeLock = readWriterLock.writeLock();

    private final int length;


    public ShareData(int length) {
        this.length = length;
        for (int i = 0; i < length; i++) {
            clist.add(i, 'c');
        }
    }

    public char[] read() throws InterruptedException {
        try {
            readLock.lock();
            char[] newBuffer = new char[length];
            for (int i = 0; i < length; i++) {
                newBuffer[i] = clist.get(i);
            }
            slowly();
            return newBuffer;
        }
        finally {
            readLock.unlock();
        }

    }

    public void writer(char c) throws InterruptedException {
        try {
            writeLock.lock();
            for (int i = 0; i < length; i++) {
                clist.add(i, c);
            }
            slowly();
        }
        finally {
            writeLock.unlock();
        }
    }




    private void slowly() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }

}
