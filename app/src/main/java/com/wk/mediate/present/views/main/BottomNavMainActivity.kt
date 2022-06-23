package com.wk.mediate.present.views.main

import android.os.Bundle
import com.wk.mediate.R
import com.wk.mediate.present.config.ViewBindingActivity
import com.wk.mediate.databinding.ActivityBottomNavMainBinding
import com.wk.mediate.present.views.main.Chatting.ChattingFragment
import com.wk.mediate.present.views.main.Home.HomeFragment

class BottomNavMainActivity : ViewBindingActivity<ActivityBottomNavMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.frame_container, HomeFragment()).commit()

        binding?.bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_container, HomeFragment()).commit()
                }
                R.id.action_tutoring -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_container, TutoringFragment()).commit()
                }
                R.id.action_community -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_container, CommunityFragment()).commit()
                }
                R.id.action_chatting -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_container, ChattingFragment()).commit()
                }
                R.id.action_mypage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_container, MypageFragment()).commit()
                }
            }

            true
        }
    }

}

