package com.example.core.entity;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-6-1
 * @version 1.0
 */
public class RespBean<T> {

    private T data;

    private String msg;

    private int code;


    public static <T> RespBean<T> ok(T data) {
        RespBean<T> bean = new RespBean<>();
        bean.setData(data);
        bean.setCode(200);
        return bean;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
