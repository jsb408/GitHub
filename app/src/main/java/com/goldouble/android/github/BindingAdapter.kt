package com.goldouble.android.github

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("setImage")
        fun setImage(view: ImageView, url: String?) {
            url?.let {
                Glide.with(view).load(it).into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("numberText")
        fun numberText(view: TextView, number: Int) {
            view.text = number.toString()
        }
    }
}