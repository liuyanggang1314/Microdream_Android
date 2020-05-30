package com.liuyanggang.microdream.entity;

/**
 * @ClassName IndexEntity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public class IndexEntity {
    private Long id;
    private String title;
    private String imageUrl;
    private String content;
    private String categoryName;
    private String postDate;
    private String totalComments;
    private String pageViews;
    private String likeCount;
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(String totalComments) {
        this.totalComments = totalComments;
    }

    public String getPageViews() {
        return pageViews;
    }

    public void setPageViews(String pageViews) {
        this.pageViews = pageViews;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
