package com.wk.mediate.ui

import android.os.Handler
import android.os.Looper
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentIntroBinding
import com.wk.mediate.ui.Login.LoginFragment
import com.wk.mediate.ui.Register.Select.Subject.SelectSubjectFragment

class IntroFragment : ViewBindingFragment<FragmentIntroBinding>() {
    override fun onResume() {
        super.onResume()
        moveNext()
    }

    private fun moveNext() {
        Handler(Looper.getMainLooper()).postDelayed({
            val fragment = LoginFragment()
            replaceFragment(fragment)
        }, 1000)
    }
}