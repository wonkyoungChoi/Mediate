package com.wk.mediate.ui.Register.BasicInfo

import android.os.Bundle
import android.view.View
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentRegisterFinishBinding
import com.wk.mediate.extensions.setAlphaClickable
import com.wk.mediate.ui.Login.LoginFragment
import com.wk.mediate.ui.Register.Select.SelectTypeFragment

class RegisterFinishFragment : ViewBindingFragment<FragmentRegisterFinishBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            tvName.text = BasicRegisterInfo.info.name

            btGoMain.setAlphaClickable(true)
            btInputInfo.setAlphaClickable(true)

            btInputInfo.setOnClickListener{
                val fragment = SelectTypeFragment()
                pushFragment(fragment)
            }

            btGoMain.setOnClickListener{
                val fragment = LoginFragment()
                replaceFragment(fragment)
            }
        }
    }
}