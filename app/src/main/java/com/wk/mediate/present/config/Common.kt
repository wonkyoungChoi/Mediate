package com.wk.mediate.present.config

import android.content.Context

class Common {
    companion object {
        lateinit var appContext: Context

        fun initialze(appContext: Context) {
            Companion.appContext = appContext
        }
    }
}