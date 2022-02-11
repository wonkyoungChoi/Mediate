package com.wk.mediate.ui.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.R
import com.wk.mediate.databinding.ActivityLoginBinding
import com.wk.mediate.ui.Register.AuthActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var passwordVisible: Boolean = false
    private lateinit var model : LoginViewModel
    private lateinit var loginValue : Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(LoginViewModel::class.java)

        focusableEditText(binding.etId, binding.tvHintId, getString(R.string.id))
        focusableEditText(binding.etPassword, binding.tvHintPassword, getString(R.string.password))

        observeResult()

        userCheckIntent()
        lookPassword()
        login()

    }

    private fun focusableEditText(et: EditText, tv: TextView, focusText: String) {
        Log.d("test","test")
        et.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                et.setPadding(50, 22, 50, 0)
                tv.text = focusText
                tv.visibility = View.VISIBLE
                et.hint = ""
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(et, 0)
            } else if(!hasFocus && et.text.isNotEmpty()){
                tv.text = focusText
                et.setPadding(50, 22, 50, 0)
            } else {
                tv.visibility = View.GONE
                et.hint = focusText
                et.setPadding(50, 18, 50, 18)
            }
        }
    }

    private fun lookPassword() {
        binding.tvLook.setOnClickListener {
            passwordVisible = true
            binding.tvHide.visibility = View.VISIBLE
            binding.tvLook.visibility = View.GONE
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT
        }

        binding.tvHide.setOnClickListener {
            passwordVisible = false
            binding.tvHide.visibility = View.GONE
            binding.tvLook.visibility = View.VISIBLE
            binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            when {
                binding.etId.text.isEmpty() ->  {
                    checkViewGone()
                    binding.tvInputIdCheck.visibility = View.VISIBLE
                    binding.checkboxRememberId.setMarginTop(70)
                }
                binding.etPassword.text.isEmpty() -> {
                    checkViewGone()
                    binding.tvInputPasswordCheck.visibility = View.VISIBLE
                    binding.checkboxRememberId.setMarginTop(70)
                }
                else -> {
                    checkViewGone()
                    binding.checkboxRememberId.setMarginTop(12)
                    loginValue = Login(binding.etId.text.toString(), binding.etPassword.text.toString())
                    model.loadLogin(loginValue)
                }
            }
        }
    }

    private fun observeResult() {
        model.getLoginResult().observe(this, {

            when (it.token) {
                "비밀번호가 틀립니다." ->  {
                    checkViewGone()
                    binding.tvInputLoginCheck.visibility = View.VISIBLE
                    binding.checkboxRememberId.setMarginTop(70)
                }
                "등록된 아이디가 없습니다." -> {
                    checkViewGone()
                    binding.tvInputRegisterCheck.visibility = View.VISIBLE
                    binding.checkboxRememberId.setMarginTop(70)
                } //회원가입 이력 X
                else -> {
                    checkViewGone()
                    binding.checkboxRememberId.setMarginTop(12)
                    Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                    Log.d("token Value", it.token.toString())
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
        binding.tvInputIdCheck.visibility = View.GONE
        binding.tvInputPasswordCheck.visibility = View.GONE
        binding.tvInputLoginCheck.visibility = View.GONE
        binding.tvInputRegisterCheck.visibility = View.GONE
    }

    private fun userCheckIntent() {
        //아이디 찾기
        binding.tvFindId.setOnClickListener {
            //intentActivity(it.context, )
        }

        //비밀번호 찾기
        binding.tvFindId.setOnClickListener {
            //intentActivity(it.context, )
        }

        //회원가입 하기
        binding.tvRegister.setOnClickListener {
            intentActivity(it.context, AuthActivity::class.java)
        }
    }

    private fun intentActivity(context : Context, c: Class<*>) {
        val intent = Intent(context, c)
        context.startActivity(intent)
    }


}