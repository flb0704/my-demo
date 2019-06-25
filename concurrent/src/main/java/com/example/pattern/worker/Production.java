package com.example.pattern.worker;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class Production extends InstructionBook{

    private final int pid;

    public Production(int pid) {
        this.pid = pid;
    }


    @Override
    protected void firstProcess() {
        System.out.println(this.pid + " is first】】】】 process");
    }

    @Override
    protected void lastProcess() {
        System.out.println(this.pid + " is last +++++++++ process");
    }
}
