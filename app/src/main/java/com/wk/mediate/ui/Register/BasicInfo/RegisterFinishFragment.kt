package com.wk.mediate.ui.Register.BasicInfo

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentRegisterFinishBinding
import com.wk.mediate.extensions.setAlphaClickable
import com.wk.mediate.ui.Register.SelectInfo.SelectTypeFragment

class RegisterFinishFragment : ViewBindingFragment<FragmentRegisterFinishBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            tvName.text = BasicRegisterInfo.info.name

            btBack.setAlphaClickable(true)
            btStartMediate.setAlphaClickable(true)
            btSelectInfo.setAlphaClickable(true)

            btSelectInfo.setOnClickListener{
                val fragment = SelectTypeFragment()
                pushFragment(fragment)
            }

            btStartMediate.setOnClickListener{
                moveToRoot()
            }

            requireActivity().onBackPressedDispatcher
                    .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            if(isEnabled) {
                                moveToRoot()
                            }
                        }
                    })
        }
    }
}