package com.example.pattern.balking;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class DocumentTest {


    public static void main(String[] args){
        new DocumentEditThread("test.txt","D:\\").start();
    }

}
