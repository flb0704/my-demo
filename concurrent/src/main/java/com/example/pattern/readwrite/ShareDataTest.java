package com.example.pattern.readwrite;


/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public class ShareDataTest {


    private static String text = "123456789abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args){

        final ShareData shareData = new ShareData(30);

        // 4个写线程
        for (int i = 0; i < 4; i++) {
            new Thread(null,()->{
                for (int j = 0; j < text.length(); j++) {
                    char c = text.charAt(j);
                    try {
                        shareData.writer(c);
                        System.out.println(Thread.currentThread() + ", write " + c);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, " 【++++++++++++write"+ i+"+++++++++++】", 0).start();
        }

        // 读线程
        for (int i = 0; i < 8; i++) {
            new Thread(null, ()->{
                while (true){
                    try {
                        String a = new String(shareData.read());
                        System.out.println(Thread.currentThread() + ", read " + a);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }, " >>> read-" + i+" <<< ", 0).start();

        }

    }

}
