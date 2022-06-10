package com.wk.mediate.common

import android.content.Context

class Common {
    companion object {
        lateinit var appContext: Context

        fun initialze(appContext: Context) {
            Common.appContext = appContext
        }
    }
}