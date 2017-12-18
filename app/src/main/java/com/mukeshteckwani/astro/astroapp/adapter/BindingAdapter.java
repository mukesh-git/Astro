package com.mukeshteckwani.astro.astroapp.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.mukeshteckwani.astro.astroapp.R;

/**
 * Created by mukeshteckwani on 18/12/17.
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter("drawable")
    public static void setImageDrawable(ImageView imageView, boolean b) {
        imageView.setImageDrawable(b ? ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_favorite_black_24dp) :
                ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_favorite_border_black_24dp));
    }
}
