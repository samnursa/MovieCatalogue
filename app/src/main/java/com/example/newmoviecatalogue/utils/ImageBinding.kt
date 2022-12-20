package com.example.newmoviecatalogue.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newmoviecatalogue.BuildConfig.BASE_URL_IMAGE
import com.example.newmoviecatalogue.R

@BindingAdapter("imageResource")
fun loadImage(view: ImageView, imageName: String) {
    if (imageName.isNotEmpty()) {
        val radius = view.context.resources.getDimensionPixelSize(R.dimen.shape)
        val image = BASE_URL_IMAGE + imageName
        val context = view.context
        Glide.with(context)
            .load(image)
            .transform(RoundedCorners(radius))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_image_loading).error(R.drawable.ic_image_error))
            .into(view)
    }
}