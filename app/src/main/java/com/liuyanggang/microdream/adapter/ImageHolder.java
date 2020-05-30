package com.liuyanggang.microdream.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName ImageHolder
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/29
 * @Version 1.0
 */
public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.imageView = (ImageView) view;
    }
}
