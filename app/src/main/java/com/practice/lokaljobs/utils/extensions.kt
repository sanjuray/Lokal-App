package com.practice.lokaljobs.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.practice.lokaljobs.MainActivity
import com.practice.lokaljobs.R
import java.util.Objects

fun Exception.errorLogs(errorLocation:String ?= "MainActivity"){
    val e: Exception = this
    Log.e("SGERROR","At $errorLocation:\n$e")
    e.printStackTrace()
}

fun Toolbar.set(
    context: Context,
    toolbarTitle: String?= "",
    enableBackButton: Boolean = true
) {
    try {
        this.title = toolbarTitle
        this.setTitleTextColor(resources.getColor(R.color.icon_grey))
        (context as MainActivity).setSupportActionBar(this)
        (context).supportActionBar?.setDisplayShowTitleEnabled(true)
        if (enableBackButton) {
            (context).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.setNavigationOnClickListener {
                try {
                    (context).onBackPressed()
                } catch (e: Exception) {
                    e.errorLogs()
                }
            }

            val drawable: Drawable = this.navigationIcon!!
            drawable.setColorFilter(
                ContextCompat.getColor(
                    Objects.requireNonNull(context),
                    R.color.black
                ), PorterDuff.Mode.SRC_ATOP
            )
        }
        else {
            (context).supportActionBar?.setLogo(R.drawable.lokal_icon_small)
            (context).supportActionBar?.setDisplayUseLogoEnabled(true)
            this.setPadding(
                resources.getDimension(R.dimen.padding_element_large).toInt(),
                this.paddingTop,
                this.paddingRight,
                this.paddingBottom
            )
        }

    } catch (e: Exception) {
        e.errorLogs()
    }
}