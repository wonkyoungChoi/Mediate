package com.wk.mediate.ui.Register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.databinding.ActivityRegisterMainBinding

class RegisterMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterMainBinding
    var i : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()

        binding.btNext.setOnClickListener{
            setCurrentItem(i++)
        }
    }

    private fun initPager() {
        val pagerAdapter = RegisterPagerAdapter(this)

        // 3개의 Fragment Add
        pagerAdapter.addFragment(RegisterTutorInfoFragment())
        pagerAdapter.addFragment(RegisterTutorInfo2Fragment())
        pagerAdapter.addFragment(RegisterFinishFragment())

        // Adapter
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false

        binding.dotsIndicator.setViewPager2(binding.viewPager)


    }

    private fun setCurrentItem(item: Int) {
        binding.viewPager.currentItem = item
    }
}