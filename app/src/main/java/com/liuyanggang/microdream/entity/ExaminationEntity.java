package com.liuyanggang.microdream.entity;

/**
 * @ClassName ExaminationEntity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/31
 * @Version 1.0
 */
public class ExaminationEntity {
    private Long id;
    private String moduleName;
    private String category;
    private String title;
    private String content;
    private String updateTime;
    private String createTime;
    private Long readingSum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getReadingSum() {
        return readingSum;
    }

    public void setReadingSum(Long readingSum) {
        this.readingSum = readingSum;
    }
}
