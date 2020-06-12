package com.liuyanggang.microdream.entity;

/**
 * @ClassName ImageEntity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/11
 * @Version 1.0
 */
public class ImageEntity {
    private Long id;
    private String name;
    private String url;
    private Long type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
