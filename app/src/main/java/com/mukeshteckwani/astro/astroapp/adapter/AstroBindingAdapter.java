package com.mukeshteckwani.astro.astroapp.adapter;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mukeshteckwani.astro.astroapp.R;

/**
 * Created by mukeshteckwani on 18/12/17.
 */

public class AstroBindingAdapter {

    @BindingAdapter("drawable")
    public static void setImageDrawable(ImageView imageView, boolean b) {
        imageView.setImageDrawable(b ? ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_favorite_black_24dp) :
                ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_favorite_border_black_24dp));
    }

    @BindingAdapter("src_compat")
    public static void setShowImage(ImageView imageView, String url) {
        if (url != null)
            Glide.with(imageView.getContext()).load(url).into(imageView);
    }


}
