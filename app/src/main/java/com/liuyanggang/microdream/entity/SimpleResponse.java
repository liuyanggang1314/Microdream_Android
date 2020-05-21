package com.liuyanggang.microdream.entity;

import java.io.Serializable;

/**
 * @ClassName SimpleResponse
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class SimpleResponse<T> implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int status;
    public String timestamp;
    public String message;

    @Override
    public String toString() {
        return "LygResponse{\n" +//
                "\tstatus=" + status + "\n" +//
                "\ttimestamp=" + timestamp + "\n" +//
                "\tmessage='" + message + "\'\n" +//
                '}';
    }
}

