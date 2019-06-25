package com.example.pattern.active;

import com.example.pattern.future.Future;
import com.example.pattern.future.FutureTask;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-24
 * @version 1.0
 */
public class ActiveFuture<T> extends FutureTask<T> {

    @Override
    protected void finish(T result) {
        super.finish(result);
    }
}
