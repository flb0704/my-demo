package com.example.pattern.balking;

import java.io.IOException;
import java.util.Scanner;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class DocumentEditThread extends Thread {

    private final String name;

    private final String path;

    private final Scanner scanner = new Scanner(System.in);

    public DocumentEditThread(String name, String path) {
        super("Edit thread");
        this.name = name;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            Document document = Document.create(path, name);
            for (int i = 0; i < 10; i++) {
                String txt = scanner.next();
                if ("quit".equals(txt)){
                    document.close();
                    break;
                }

                document.edit(txt);
                // 模拟用户在一定间隔后手动保存
                if (i % 5 == 0){
                    document.save();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
