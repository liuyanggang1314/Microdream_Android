package com.liuyanggang.microdream.entity;

import java.io.Serializable;

/**
 * @ClassName ServerModel
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class ServerModel implements Serializable {
    private static final long serialVersionUID = -828322761336296999L;

    public String status;
    public String timestamp;
    public String message;

    @Override
    public String toString() {
        return "ServerModel{\n" +//
                "\tstatus='" + status + "\'\n" +//
                "\ttimestamp='" + timestamp + "\'\n" +//
                "\tmessage='" + message + "\'\n" +//
                '}';
    }
}
