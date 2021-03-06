package com.goldouble.android.github

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import net.cachapa.expandablelayout.ExpandableLayout

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

        @JvmStatic
        @BindingAdapter("isVisible")
        fun isVisible(view: View, isVisible: Boolean) {
            view.isVisible = isVisible
        }

        @JvmStatic
        @BindingAdapter("isActivated")
        fun isActivated(view: View, isActivated: Boolean) {
            view.isActivated = isActivated
        }

        @JvmStatic
        @BindingAdapter("isExpanded")
        fun isExpanded(view: ExpandableLayout, isExpanded: Boolean) {
            view.isExpanded = isExpanded
        }
    }
}