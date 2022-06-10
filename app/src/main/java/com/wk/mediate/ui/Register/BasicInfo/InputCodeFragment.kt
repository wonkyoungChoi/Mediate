package com.wk.mediate.ui.Register.BasicInfo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.wk.mediate.R
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.components.toast.ToastView
import com.wk.mediate.databinding.FragmentInputCodeBinding
import com.wk.mediate.extensions.afterTextChanged
import com.wk.mediate.extensions.focusEditTextChange
import com.wk.mediate.extensions.setAlphaClickable
import com.wk.mediate.extensions.setMarginTop

class InputCodeFragment : ViewBindingFragment<FragmentInputCodeBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            btNextActive.setAlphaClickable(true)
            btBack.setAlphaClickable(true)

            etCode.focusEditTextChange(requireActivity(), tvHintInputCode, getString(R.string.input_code_hint))
            etCode.afterTextChanged {
                if(etCode.text.isEmpty()) {
                    btNextActive.visibility = View.GONE
                    btNextInactive.visibility = View.VISIBLE
                } else {
                    btNextActive.visibility = View.VISIBLE
                    btNextInactive.visibility = View.GONE
                }
            }
        }

        requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if(isEnabled) {
                            showConfirm(requireContext().getString(R.string.alert_cancel_register), requireContext().getString(R.string.alert_cancel_register_out), requireContext().getString(R.string.confirm) ,{
                                isEnabled = false
                                moveToRoot()
                                Toast.makeText(requireContext(),requireContext().getString(R.string.cancel_register), Toast.LENGTH_SHORT).show()
                                BasicRegisterInfo.info = BasicInfo("", "", "", "")
                            }, requireContext().getString(R.string.cancel), {})
                        }
                    }
                })

        onClick()
    }

    private fun onClick() {
        //다음 버튼 클릭
        binding?.run {
            btNextActive.setOnClickListener {
                //코드 검증 해야함
                if(true) {
                    val fragment = InputIdPasswordFragment()
                    pushFragment(fragment)
                } else {
                    tvInputCodeCheck.visibility = View.VISIBLE
                    tvCodeInfo.setMarginTop(70)
                }
            }

            //뒤로가기 클릭
            btBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }



}