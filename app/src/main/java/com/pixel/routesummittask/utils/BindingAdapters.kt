package com.pixel.routesummittask.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.pixel.routesummittask.R
import com.squareup.picasso.Picasso

class BindingAdapters {
    companion object {
        @BindingAdapter("urlCircular")
        @JvmStatic
        fun bindImageCircular(
            imageView: ImageView,
            url: String?,
        ) {
            Picasso
                .get()
                .load(url)
                .placeholder(R.drawable.ic_category_placeholder)
                .transform(TransformCircular())
                .into(imageView)
        }

        @BindingAdapter("url")
        @JvmStatic
        fun bindImage(
            imageView: ImageView,
            url: String?,
        ) {
            Picasso
                .get()
                .load(url)
                .centerCrop()
                .fit()
                .into(imageView)
        }
    }
}

@BindingAdapter("errorMessage")
fun setTextInputLayoutError(
    textInputLayout: TextInputLayout,
    error: String?,
) {
    textInputLayout.error = error
}

@BindingAdapter("clearFocusOnCondition")
fun clearFocusOnCondition(
    view: View,
    condition: Boolean,
) {
    if (condition) {
        view.clearFocus()
    }
}
