package com.liuyanggang.microdream.entity;

import java.io.Serializable;

/**
 * @ClassName LygResponse
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class LygResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;


    public T data;

    @Override
    public String toString() {
        return "LygResponse{\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }
}
