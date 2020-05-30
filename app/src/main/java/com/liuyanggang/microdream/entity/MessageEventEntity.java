package com.liuyanggang.microdream.entity;

/**
 * @ClassName MessageEvent
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public class MessageEventEntity {
    private  String message;
    public MessageEventEntity(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
