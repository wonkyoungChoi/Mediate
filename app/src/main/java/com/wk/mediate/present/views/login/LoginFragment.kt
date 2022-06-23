package com.wk.mediate.present.views.login

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.wk.mediate.R
import com.wk.mediate.data.models.Login
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.present.config.LoadingProgress
import com.wk.mediate.present.config.Pref
import com.wk.mediate.databinding.FragmentLoginBinding
import com.wk.mediate.present.extension.focusEditTextChange
import com.wk.mediate.present.utils.DataHandler
import com.wk.mediate.present.views.main.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {
    private var passwordVisible: Boolean = false
    private val viewModel : LoginViewModel by viewModels()
    private lateinit var loginValue : Login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            etId.focusEditTextChange(requireActivity(), tvHintId, getString(R.string.id))
            etPassword.focusEditTextChange(requireActivity(), tvHintPassword, getString(R.string.password))

            //아이디 찾기
            tvFindId.setOnClickListener {
                //intentActivity(it.context, )
            }

            //비밀번호 찾기
            tvFindId.setOnClickListener {
                //intentActivity(it.context, )
            }

            //회원가입 하기
            tvRegister.setOnClickListener {
                val fragment = HomeFragment()
                pushFragment(fragment)
            }

            //로그인
            btnLogin.setOnClickListener {
                when {
                    etId.text.isEmpty() ->  {
                        checkViewGone()
                        tvInputIdCheck.visibility = View.VISIBLE
                        checkboxRememberId.setMarginTop(70)
                    }
                    etPassword.text.isEmpty() -> {
                        checkViewGone()
                        tvInputPasswordCheck.visibility = View.VISIBLE
                        checkboxRememberId.setMarginTop(70)
                    }
                    else -> {
                        checkViewGone()
                        checkboxRememberId.setMarginTop(12)
                        loginValue = Login(etId.text.toString(), Pref.fcmToken, etPassword.text.toString())
                        viewModel.getLivedataLoginResult(loginValue)
                    }
                }
            }
        }

        observeLoginResult()
        lookPassword()

    }

    private fun lookPassword() {
        binding?.run {
            tvLook.setOnClickListener {
                passwordVisible = true
                tvHide.visibility = View.VISIBLE
                tvLook.visibility = View.GONE
                etPassword.inputType = InputType.TYPE_CLASS_TEXT
            }

            tvHide.setOnClickListener {
                passwordVisible = false
                tvHide.visibility = View.GONE
                tvLook.visibility = View.VISIBLE
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    LoadingProgress.shared.dismiss()
                    binding?.run {
                        when (dataHandler.data?.accessToken) {
                            "" -> {
                                checkViewGone()
                                tvInputLoginCheck.visibility = View.VISIBLE
                                checkboxRememberId.setMarginTop(70)
                            }
//                    "등록된 아이디가 없습니다." -> {
//                        checkViewGone()
//                        tvInputRegisterCheck.visibility = View.VISIBLE
//                        checkboxRememberId.setMarginTop(70)
//                    } //회원가입 이력 X
                            else -> {
                                checkViewGone()
                                checkboxRememberId.setMarginTop(12)
                                Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT)
                                    .show()
                                Pref.auth = dataHandler.data

                                //TODO 메인화면으로 이동하게 바꾸기
                                val fragment = ProfileImageTestFragment()
                                pushFragment(fragment)
                            }

                        }
                    }
                }
                is DataHandler.ERROR -> {
                    if(dataHandler.message.toString().contains("비밀번호가 일치하지 않습니다.")) {
                        checkViewGone()
                        binding?.run {
                            tvInputLoginCheck.visibility = View.VISIBLE
                            checkboxRememberId.setMarginTop(70)
                        }
                    } else if(dataHandler.message.toString().contains("해당 ID의 엔티티를 찾을 수 없습니다.")) {
                        checkViewGone()
                        binding?.run {
                            tvInputRegisterCheck.visibility = View.VISIBLE
                            checkboxRememberId.setMarginTop(70)
                        }

                    }
                    LoadingProgress.shared.dismiss()
                }
                is DataHandler.LOADING -> {
                    LoadingProgress.shared.show(requireActivity())
                }
                is DataHandler.FAIL -> {
                    //TODO FAIL 처리
                }
            }

        }
    }

    private fun View.setMarginTop(topMargin: Int) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin)
        layoutParams = params
    }

    private fun checkViewGone() {
        binding?.run {
            tvInputIdCheck.visibility = View.GONE
            tvInputPasswordCheck.visibility = View.GONE
            tvInputLoginCheck.visibility = View.GONE
            tvInputRegisterCheck.visibility = View.GONE
        }
    }
}