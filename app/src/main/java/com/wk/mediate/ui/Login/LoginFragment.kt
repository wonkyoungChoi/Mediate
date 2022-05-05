package com.wk.mediate.ui.Login

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.R
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentLoginBinding
import com.wk.mediate.extensions.focusEditTextChange
import com.wk.mediate.ui.Register.BasicInfo.AuthFragment

class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {
    private var passwordVisible: Boolean = false
    private lateinit var model : LoginViewModel
    private lateinit var loginValue : Login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this).get(LoginViewModel::class.java)

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
                Log.d("CLCIC", "CLCICKLCL")
                val fragment = AuthFragment()
                pushFragment(fragment)
            }
        }

        observeResult()

        lookPassword()
        login()
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

    private fun login() {
        binding?.run {
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
                        loginValue = Login(etId.text.toString(), etPassword.text.toString())
                        model.loadLogin(loginValue)
                    }
                }
            }
        }
    }

    private fun observeResult() {
        model.getLoginResult().observe(viewLifecycleOwner, {
            binding?.run {
                when (it.token) {
                    "비밀번호가 틀립니다." ->  {
                        checkViewGone()
                        tvInputLoginCheck.visibility = View.VISIBLE
                        checkboxRememberId.setMarginTop(70)
                    }
                    "등록된 아이디가 없습니다." -> {
                        checkViewGone()
                        tvInputRegisterCheck.visibility = View.VISIBLE
                        checkboxRememberId.setMarginTop(70)
                    } //회원가입 이력 X
                    else -> {
                        checkViewGone()
                        checkboxRememberId.setMarginTop(12)
                        Toast.makeText(requireContext(), "로그인 성공", Toast.LENGTH_SHORT).show()
                        Log.d("token Value", it.token.toString())
                    }

                }
            }
        })
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