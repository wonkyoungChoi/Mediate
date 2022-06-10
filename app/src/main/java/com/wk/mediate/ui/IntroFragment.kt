package com.wk.mediate.ui

import android.os.Handler
import android.os.Looper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.common.Pref
import com.wk.mediate.databinding.FragmentIntroBinding
import com.wk.mediate.ui.Login.LoginFragment

class IntroFragment : ViewBindingFragment<FragmentIntroBinding>() {
    override fun onResume() {
        super.onResume()

        getToken()
    }

    private fun moveNext() {
        Handler(Looper.getMainLooper()).postDelayed({
            val fragment = LoginFragment()
            replaceFragment(fragment)
        }, 1000)
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            //저장해둔 토큰값과 FCM값이 달라지면 서버 FCM 업데이트
            if(Pref.fcmToken != token) {
                Pref.fcmToken = token
            }
            moveNext()
        })
    }
}