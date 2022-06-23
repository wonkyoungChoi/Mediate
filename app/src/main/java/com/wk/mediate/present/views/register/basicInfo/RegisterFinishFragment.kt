package com.wk.mediate.present.views.register.basicInfo

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.wk.mediate.data.models.BasicRegisterInfo
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.databinding.FragmentRegisterFinishBinding
import com.wk.mediate.present.extension.setAlphaClickable
import com.wk.mediate.present.views.register.selectInfo.SelectTypeFragment

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