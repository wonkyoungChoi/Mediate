package com.wk.mediate

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.wk.mediate.common.Common
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MediateApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Common.initialze(applicationContext)
    }
}