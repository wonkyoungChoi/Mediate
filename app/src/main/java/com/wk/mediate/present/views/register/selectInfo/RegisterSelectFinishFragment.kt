package com.wk.mediate.present.views.register.selectInfo

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.wk.mediate.R
import com.wk.mediate.data.models.BasicRegisterInfo
import com.wk.mediate.data.models.SelectRegisterInfo
import com.wk.mediate.databinding.FragmentRegisterSelectFinishBinding
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.present.extension.setAlphaClickable

class RegisterSelectFinishFragment : ViewBindingFragment<FragmentRegisterSelectFinishBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            tvName.text = BasicRegisterInfo.info.name
            btStartMediate.setAlphaClickable(true)
            btBack.setAlphaClickable(true)

            tvName.text = BasicRegisterInfo.info.name

            if(SelectRegisterInfo.type == "TUTOR") {
                tvTutInfo.text = getString(R.string.tutor)
            } else {
                tvTutInfo.text = getString(R.string.tutee)
            }

            btStartMediate.setOnClickListener{
                //로그인 로직 넣기
                moveToRoot()
            }


            //로그인 화면으로 갈지?
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