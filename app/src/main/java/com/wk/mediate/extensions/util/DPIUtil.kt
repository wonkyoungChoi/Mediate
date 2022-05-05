package com.wk.mediate.extensions.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.Log
import android.view.View

class DPIUtil {


    companion object {
        var metrics: DisplayMetrics? = null
            private set

        val TAG = "DPIUtil"
        private val ERROR = "Needed to be init with Activity"
        fun updateMatrics(activity: Activity) { // dp 변경된 경우
            metrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            activity.windowManager.defaultDisplay.getMetrics(metrics)
        }

        fun dp2px(dp: Float): Float {
            if (null == metrics) {
                Log.e(DPIUtil.TAG, DPIUtil.ERROR)
                return 0F
            }
            return (metrics?.density ?: 0F) * dp
        }

        fun px2dp(px: Float): Float {
            if (null == metrics) {
                Log.e(DPIUtil.TAG, DPIUtil.ERROR)
                return 0F
            }
            return px / (metrics?.density ?: 0F)
        }

        val widthPixels: Int
            get() {
                if (null == metrics) {
                    Log.e(DPIUtil.TAG, DPIUtil.ERROR)
                    return 0
                }
                return metrics?.widthPixels ?: 0
            }

        val heightPixels: Int
            get() {
                if (null == metrics) {
                    Log.e(DPIUtil.TAG, DPIUtil.ERROR)
                    return 0
                }
                return metrics?.heightPixels ?: 0
            }

        val metricsInfo: String?
            get() {
                if (null == metrics) {
                    Log.e(DPIUtil.TAG, DPIUtil.ERROR)
                    return DPIUtil.ERROR
                }
                val sb = StringBuffer()
                sb.append("densityDpi : ").append(metrics?.densityDpi).append("\n")
                sb.append("density : ").append(metrics?.density).append("\n")
                sb.append("W : ").append(metrics?.widthPixels).append(", H : ")
                        .append(metrics?.heightPixels).append("\n")
                return sb.toString()
            }

        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId =
                    context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        fun getViewRect(v: View): Rect? {
            val location = IntArray(2)
            v.getLocationInWindow(location)
            val width = v.width
            val height = v.height
            return Rect(
                    location[0],
                    location[1],
                    location[0] + width,
                    location[1] + height
            )
        }

    }




}