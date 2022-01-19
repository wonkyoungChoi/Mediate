package com.wk.mediate

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.databinding.ActivityLoginBinding
import com.wk.mediate.ui.Register.RegisterAuthActivity
import com.wk.mediate.ui.Register.RegisterMainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var passwordVisible: Boolean = false
    private var requestCode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userCheckIntent()
        lookPassword()
        login()

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
            //requestCode 받아오기

            when {
                binding.etId.text.isEmpty() ->  binding.tvInputIdCheck.visibility = View.VISIBLE
                binding.etPassword.text.isEmpty() -> binding.tvInputPasswordCheck.visibility = View.VISIBLE
                requestCode == 404 -> binding.tvInputLoginCheck.visibility = View.VISIBLE  //로그인 실패
                requestCode == 202 -> binding.tvInputRegisterCheck.visibility = View.VISIBLE //회원가입 이력 X
                requestCode == 100 -> binding.tvInputRegisterCheck.visibility = View.VISIBLE //홈 화면으로 넘어가기
                else ->  {
                    binding.tvInputIdCheck.visibility = View.GONE
                    binding.tvInputPasswordCheck.visibility = View.GONE
                    binding.tvInputLoginCheck.visibility = View.GONE
                    binding.tvInputRegisterCheck.visibility = View.GONE
                }
            }
        }
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
            intentActivity(it.context, RegisterAuthActivity::class.java)
        }
    }

    private fun intentActivity(context : Context, c: Class<*>) {
        val intent = Intent(context, c)
        context.startActivity(intent)
    }


}