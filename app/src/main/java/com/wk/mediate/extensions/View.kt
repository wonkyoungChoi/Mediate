package com.wk.mediate.extensions

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

fun View.setAlphaClickable(isAlpha: Boolean) {
    if(!isAlpha) {
        setOnTouchListener(null)
        this.isClickable = isAlpha
        return
    }

    this.isClickable = isAlpha
    this.setOnTouchListener{ v, event ->
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.alpha = 0.7F
            }
            MotionEvent.ACTION_UP -> {
                v.alpha = 1F
                (parent as View).performClick()
            }
            MotionEvent.ACTION_CANCEL -> v.alpha = 1F
        }
        return@setOnTouchListener false
    }
}

fun View.setMarginTop(topMargin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin)
    layoutParams = params
}