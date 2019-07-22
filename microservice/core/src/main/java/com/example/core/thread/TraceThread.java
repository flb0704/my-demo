package com.example.core.thread;

import com.example.core.utils.LogUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-7-9
 * @version 1.0
 */
public class TraceThread implements Runnable{

    @Override
    public void run() {
        LogUtil.info("####### before task ");
        int a = new Random().nextInt(500);
        a = a < 0 ? 500 : a + 300;
        try {
            TimeUnit.MILLISECONDS.sleep(a);
            LogUtil.info("############## task finish ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.info("############################ task exit ");
    }
}
