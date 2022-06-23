package com.wk.mediate.present.config

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wk.mediate.R
import com.wk.mediate.present.utils.DPIUtil

class LoadingProgress {
    private var dialog: Dialog? = null
    private val isShowing: Boolean
        get() {
            if (dialog == null) return false
            return dialog?.isShowing == true
        }

    @SuppressLint("InflateParams")
    fun show(context: Context?) {
        if (context == null ) {
            return
        }
        if (dialog != null && (dialog?.isShowing == false)) {
            dialog?.cancel()
            dialog = null
        }
        if (dialog == null && !(context as Activity).isFinishing) {
            //Activity 종료로 인한 Crash 방지
            dialog = Dialog(context, R.style.TransparentProgressDialog)
            dialog?.setCancelable(false)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutLoading: View = inflater.inflate(R.layout.loading_image_view, null, false)
            dialog?.addContentView(
                    layoutLoading, ViewGroup.LayoutParams(
                    DPIUtil.dp2px(80F).toInt(),
                    DPIUtil.dp2px(110F).toInt()
            )
            )
            dialog?.show()
        }
    }

    fun dismiss() {
        try {
            if( isShowing ) {
                dialog?.dismiss()
                dialog = null
            }
        } catch (e: Exception) {
            // nothing
        }
    }

    companion object {
        val shared: LoadingProgress = LoadingProgress()
    }
}