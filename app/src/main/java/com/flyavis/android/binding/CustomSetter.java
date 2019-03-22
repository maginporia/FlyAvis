package com.flyavis.android.binding;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

public class CustomSetter {
    @BindingAdapter("bind:setImageByteArray")
    public static void loadImage(ImageView view, byte[] bytes) {
        Glide
                .with(view.getContext())
                .load(bytes)
                .centerCrop()
                .into(view);
    }
}
