package com.liuyanggang.microdream.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liuyanggang.microdream.R;

/**
 * @ClassName ImageTitleHolder
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public class ImageTitleHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView title;

    public ImageTitleHolder(@NonNull View view) {
        super(view);
        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.bannerTitle);
    }
}