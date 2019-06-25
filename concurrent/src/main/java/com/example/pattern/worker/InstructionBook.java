package com.example.pattern.worker;

/**
 * Description: 说明书
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public abstract class InstructionBook {

    protected abstract void firstProcess();
    protected abstract void lastProcess();

    public final void create(){
        this.firstProcess();
        this.lastProcess();
    }




}
