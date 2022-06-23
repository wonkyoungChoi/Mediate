package com.wk.mediate.present.config

import androidx.multidex.MultiDexApplication
import com.wk.mediate.present.config.Common
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MediateApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Common.initialze(applicationContext)
    }
}