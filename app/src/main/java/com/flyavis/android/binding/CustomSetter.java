package com.flyavis.android.binding;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

public class CustomSetter {
    @BindingAdapter("app:setImageByteArray")
    public static void loadImageByteArray(ImageView view, byte[] bytes) {
        Glide
                .with(view.getContext())
                .load(bytes)
                .centerCrop()
                .into(view);
    }

    @BindingAdapter("app:setImageUrl")
    public static void loadImageUrl(ImageView view, Uri uri) {
        if (uri != null) {
            Glide
                    .with(view.getContext())
                    .load(uri)
                    .centerCrop()
                    .into(view);
        }
    }
}
