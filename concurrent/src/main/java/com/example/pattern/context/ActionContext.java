package com.example.pattern.context;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-23
 * @version 1.0
 */
public class ActionContext {

    private static final ThreadLocal<Context> context =
            ThreadLocal.withInitial(Context::new);

    public static Context get(){
        return context.get();
    }

    static class Context{

        private Object config;

        public Object getConfig() {
            return config;
        }

        public void setConfig(Object config) {
            this.config = config;
        }
    }

}
