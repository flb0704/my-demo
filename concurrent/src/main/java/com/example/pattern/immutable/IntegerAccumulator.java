package com.example.pattern.immutable;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public final class IntegerAccumulator {

    private final int init;

    public IntegerAccumulator(IntegerAccumulator accumulator, int init) {
        this.init = accumulator.getValue() + init;
    }

    public IntegerAccumulator(int i){
        this.init = i;
    }

    public IntegerAccumulator add(int i){
        return new IntegerAccumulator(this, i);
    }

    public int getValue(){
        return this.init;
    }

    private static void slowly()  {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        // 因为是不可变的，所以这个对象内的值，永远那都是1
        IntegerAccumulator accumulator = new IntegerAccumulator(1);
        IntStream.range(0,4).forEach(i->{
            new Thread(()->{
                for (int j = 0;; j++) {
                    // old得到的值，永远都是1
                    int old = accumulator.getValue();
                    int result = accumulator.add(j).getValue();
                    System.out.println(old + "+" + j + "=" + result);
                    if ((old + j) != result){
                        System.err.println("ERROR" + old + "+" + j + "!=" + result);
                    }
                    slowly();
                }
            }).start();
        });
    }


}
