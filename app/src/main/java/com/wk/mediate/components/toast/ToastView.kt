package com.wk.mediate.components.toast

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar
import com.wk.mediate.base.BaseActivity
import com.wk.mediate.databinding.ViewToastBinding
import com.wk.mediate.extensions.util.DPIUtil
import kotlin.math.min

object ToastView {

    fun show(context: Context?, message: String) {
        if( context is BaseActivity) {
            show(context, message)
        }
    }

    fun show(activity: BaseActivity, message: String) {
        val binding = ViewToastBinding.inflate(LayoutInflater.from(activity))

        binding.textToast.text = message


        val textWidth = textWidth(binding.textToast, message)

        val swidth = Resources.getSystem().displayMetrics.widthPixels
        val maxTextWidth = swidth - DPIUtil.dp2px(80f).toInt()

        val dp20 = DPIUtil.dp2px(20f).toInt()

        val width = min(textWidth, maxTextWidth)
        val sidePadding = (swidth - (width + (dp20 * 2))) / 2
        val snackbar = Snackbar.make(activity.window.decorView, "", 4000)

        snackbar.animationMode = ANIMATION_MODE_FADE


        val layout = snackbar.view as Snackbar.SnackbarLayout

        val navH = navHeight(activity)

        with(layout) {
            removeAllViews()

            setPadding(sidePadding, 0, sidePadding, DPIUtil.dp2px(84f).toInt() + navH)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))

            addView(binding.root)

            layout.setOnTouchListener { v, event -> false }
        }
        snackbar.show()

    }

    private fun navHeight(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    private fun textWidth(textView: TextView, text: String): Int {
        val bounds = Rect()
        val textPaint: Paint = textView.paint
        textPaint.getTextBounds(text, 0, text.length, bounds)
        return bounds.width() + 10
    }

}