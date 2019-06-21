package com.example.pattern.monitor;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-21
 * @version 1.0
 */
public class ObservableThread<T> extends Thread implements Observable{

    private final TaskLifecycle<T> lifecycle;

    private final Task<T> task;

    private Cycle cycle;

    public ObservableThread(Task<T> task){
        this(new TaskLifecycle.EmptLifecycle<>(), task);
    }


    public ObservableThread(TaskLifecycle<T> lifecycle, Task<T> task) {
        this.lifecycle = lifecycle;
        this.task = task;
    }


    @Override
    public final void run() {
        this.update(Cycle.STARTED,null,null);
        try {
            this.update(Cycle.RUNNING,null,null);
            T result = this.task.call();
            this.update(Cycle.DONE,result,null);
        }
        catch (Exception e){
            this.update(Cycle.ERROR, null, e);
        }

    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }


    private void update(Cycle cycle, T result, Exception e){
        this.cycle = cycle;
        if (lifecycle == null){
            return;
        }
        try {
            switch (cycle) {
                case STARTED:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(), e);
                    break;
                default:
                    break;
            }
        }
        catch (Exception e1){
            if (cycle == Cycle.ERROR){
                throw e1;
            }
        }
    }

}
