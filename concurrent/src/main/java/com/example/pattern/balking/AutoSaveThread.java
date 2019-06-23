package com.example.pattern.balking;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class AutoSaveThread extends Thread{

    private final Document doc;

    public AutoSaveThread(Document document) {
        super("AutoSaveThread ");
        this.doc = document;
    }

    @Override
    public void run() {
        while (true){
            try {
                doc.save();
                TimeUnit.SECONDS.sleep(1L);
            } catch (IOException | InterruptedException  e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
