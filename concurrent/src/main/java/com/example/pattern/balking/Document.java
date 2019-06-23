package com.example.pattern.balking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class Document {

    private boolean change;

    private List<String> content = new ArrayList<>();

    private final FileWriter writer;

    private static AutoSaveThread saveThread;

    private Document(String path, String name) throws IOException {
        this.writer = new FileWriter(new File(path,name));
    }

    public static Document create(String path, String name) throws IOException {
        Document document = new Document(path,name);
        saveThread = new AutoSaveThread(document);
        saveThread.start();
        return document;
    }

    public void edit(String content){
        synchronized (this){
            this.content.add(content);
            this.change = true;
        }
    }

    public void close() throws IOException {
        saveThread.interrupt();
        writer.close();
    }

    public void save() throws IOException {
        synchronized (this){
            if (!change){
                return;
            }
            System.out.println(Thread.currentThread()+" execute save");
            for (String c : content){
                this.writer.write(c);
                this.writer.write(" ");
            }
            this.writer.flush();
            this.change = false;
            this.content.clear();
        }
    }



}
