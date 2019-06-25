package com.example.pattern.worker;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class ProductionChannel {

    private static final int MAX_PROD = 10;

    private final Production[] prods;

    private int head;

    private int tail;

    private int total;

    private final Worker[] workers;

    public ProductionChannel(int workerSize){
        this.workers = new Worker[workerSize];

        this.prods = new Production[MAX_PROD];
        for (int i = 0; i < workerSize; i++) {
            this.workers[i] = new Worker("work" + i, this);
            this.workers[i].start();
        }
    }


    public synchronized void offerProduction(Production production){
        while (total >= prods.length){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // ignore
            }
        }
        prods[tail] = production;
        tail = (tail + 1) % prods.length;
        total++;
        this.notifyAll();
    }

    public synchronized Production takeProduction(){
        while (total<=0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // ignore
            }
        }
        Production productions = prods[head];
        head = (head + 1) % prods.length;
        total--;
        this.notifyAll();
        return productions;
    }

}
