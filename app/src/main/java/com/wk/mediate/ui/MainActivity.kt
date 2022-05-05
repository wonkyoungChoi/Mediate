package com.wk.mediate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wk.mediate.R
import com.wk.mediate.base.ViewBindingActivity
import com.wk.mediate.databinding.ActivityMainBinding

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = IntroFragment()
        replaceFragment(fragment)
    }
}