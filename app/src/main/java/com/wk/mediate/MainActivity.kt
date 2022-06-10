package com.wk.mediate

import android.os.Bundle
import com.wk.mediate.base.ViewBindingActivity
import com.wk.mediate.databinding.ActivityMainBinding
import com.wk.mediate.ui.IntroFragment

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = IntroFragment()
        replaceFragment(fragment)
    }
}