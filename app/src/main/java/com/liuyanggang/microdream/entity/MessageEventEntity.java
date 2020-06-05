package com.liuyanggang.microdream.entity;

/**
 * @ClassName MessageEvent
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public class MessageEventEntity {
    private Integer type;
    private String message;

    public MessageEventEntity(Integer type, String message) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
