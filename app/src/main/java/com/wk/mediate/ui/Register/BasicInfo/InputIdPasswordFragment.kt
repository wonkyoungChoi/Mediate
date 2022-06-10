package com.wk.mediate.ui.Register.BasicInfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.R
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.components.toast.ToastView
import com.wk.mediate.databinding.FragmentInputIdPasswordBinding
import com.wk.mediate.extensions.afterTextChanged
import com.wk.mediate.extensions.focusEditTextChange
import com.wk.mediate.extensions.setAlphaClickable
import java.util.regex.Matcher
import java.util.regex.Pattern

class InputIdPasswordFragment : ViewBindingFragment<FragmentInputIdPasswordBinding>() {
    private lateinit var model : RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this).get(RegisterViewModel::class.java)
        observeResult()

        binding?.run {
            tvName.text = BasicRegisterInfo.info.name

            btNextActive.setAlphaClickable(true)
            btBack.setAlphaClickable(true)

            //뒤로가기 클릭
            btBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            etId.focusEditTextChange(requireActivity(), tvHintInputId, getString(R.string.input_id_hint), "아이디")
            etPassword.focusEditTextChange(requireActivity(), tvHintInputPassword, getString(R.string.input_password_hint), "비밀번호")
            etPasswordCheck.focusEditTextChange(requireActivity(), tvHintInputPasswordCheck, getString(R.string.input_password_hint), "비밀번호 재입력")

            etPassword.afterTextChanged {
                if(passwordValidation(etPassword.text.toString())) {
                    passwordCheck.visibility = View.VISIBLE
                } else {
                    passwordCheck.visibility = View.GONE
                }
            }

            etPasswordCheck.afterTextChanged {
                if(passwordCheckValidation(etPassword.text.toString(), etPasswordCheck.text.toString())) {
                    passwordReCheck.visibility = View.VISIBLE
                    btNextActive.visibility = View.VISIBLE
                    btNextInactive.visibility = View.GONE

                } else {
                    passwordReCheck.visibility = View.GONE
                    btNextInactive.visibility = View.VISIBLE
                    btNextActive.visibility = View.GONE
                }
            }

            btNextActive.setOnClickListener {
                BasicRegisterInfo.info.accountId = etId.text.toString()
                BasicRegisterInfo.info.password = etPassword.text.toString()
                model.loadBasicRegister(BasicRegisterInfo.info)
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
        }
    }

    private fun passwordValidation(password: String): Boolean {
        Log.d("validation", "validation")
        val valSymbol = """([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])""";
        val valAlpha = """([a-z].*[A-Z])|([A-Z].*[a-z])""";

        val patternSymbol : Pattern  = Pattern.compile(valSymbol)
        val patternAlpha : Pattern = Pattern.compile(valAlpha);

        val matcherSymbol : Matcher = patternSymbol.matcher(password);
        val matcherAlpha : Matcher = patternAlpha.matcher(password);

        return matcherSymbol.find() && matcherAlpha.find()
    }

    private fun passwordCheckValidation(password: String, passwordCheck: String): Boolean {
        return password == passwordCheck
    }

    private fun observeResult() {
        model.getBasicRegisterResult().observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            if(it.contains("회원가입이 완료되었습니다.")) {
                val fragment = RegisterFinishFragment()
                pushFragment(fragment)
            }

        })
    }

}