package com.wk.mediate.present.views

import android.os.Bundle
import com.wk.mediate.present.config.ViewBindingActivity
import com.wk.mediate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = IntroFragment()
        replaceFragment(fragment)
    }
}