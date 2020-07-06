package com.liuyanggang.microdream.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @ClassName HomepageEntity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class HomepageEntity implements Parcelable {
    private Long id;
    private Long userId;
    private String nikeName;
    private String userName;
    private String avatar;
    private String content;
    private String creatTime;
    private boolean delete;
    private ArrayList<String> images;
    private String video;

    public HomepageEntity() {

    }

    public HomepageEntity(Parcel in) {
        this.content = in.readString();
        this.images = in.createStringArrayList();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public static Creator<HomepageEntity> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.content);
        parcel.writeStringList(this.images);
    }

    public static final Parcelable.Creator<HomepageEntity> CREATOR = new Parcelable.Creator<HomepageEntity>() {
        @Override
        public HomepageEntity createFromParcel(Parcel source) {
            return new HomepageEntity(source);
        }

        @Override
        public HomepageEntity[] newArray(int size) {
            return new HomepageEntity[size];
        }
    };
}
