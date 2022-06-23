package com.wk.mediate.present.views.register.basicInfo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.wk.mediate.R
import com.wk.mediate.data.models.BasicInfo
import com.wk.mediate.data.models.BasicRegisterInfo
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.present.config.LoadingProgress
import com.wk.mediate.databinding.FragmentInputIdPasswordBinding
import com.wk.mediate.present.extension.afterTextChanged
import com.wk.mediate.present.extension.focusEditTextChange
import com.wk.mediate.present.extension.setAlphaClickable
import com.wk.mediate.present.utils.DataHandler
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern

@AndroidEntryPoint
class InputIdPasswordFragment : ViewBindingFragment<FragmentInputIdPasswordBinding>() {
    private val viewModel : BasicRegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO 비밀번호 체크 로직 확인하기
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
                viewModel.getLivedataBasicRegisterResult(BasicRegisterInfo.info)
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
        viewModel.registerResult.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    LoadingProgress.shared.dismiss()
                    binding?.run {
                        when (dataHandler.data?.body) {
                            "회원가입이 완료되었습니다." -> {
                                Toast.makeText(context, dataHandler.data.body, Toast.LENGTH_SHORT)
                                    .show()
                                val fragment = RegisterFinishFragment()
                                pushFragment(fragment)
                            }
//                    "등록된 아이디가 없습니다." -> {
//                        checkViewGone()
//                        tvInputRegisterCheck.visibility = View.VISIBLE
//                        checkboxRememberId.setMarginTop(70)
//                    } //회원가입 이력 X
                            else -> {
                                Toast.makeText(context, dataHandler.data?.body, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                    }
                }
                is DataHandler.ERROR -> {
                    LoadingProgress.shared.dismiss()
                }
                is DataHandler.LOADING -> {
                    LoadingProgress.shared.show(requireActivity())
                }
            }

        }
    }

}