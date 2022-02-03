package com.wk.mediate.ui.Register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.databinding.ActivityInputIdPasswordBinding

class InputIdPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputIdPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputIdPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //editTextWatcher()
        focusableEditText(binding.etId, binding.tvHintInputId, "아이디")
        focusableEditText(binding.etPassword, binding.tvHintInputPassword, "비밀번호")
        focusableEditText(binding.etPasswordCheck, binding.tvHintInputPasswordCheck, "비밀번호 재입력")

        etWatcher(binding.etPassword, true, binding.passwordCheck)
        etWatcher(binding.etPasswordCheck, true, binding.passwordReCheck)

        binding.btNextActive.setOnClickListener {
            //코드 검증 해야함
            if(true) {
                val intent = Intent(this, SelectTypeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.tvInputCodeCheck.visibility = View.VISIBLE
                binding.tvCodeInfo.setMarginTop(70)
            }

        }

    }

    private fun focusableEditText(et: EditText, tv: TextView, text: String) {
        Log.d("test","test")
        et.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                Log.d("test","test")
                et.setPadding(50, 22, 50, 0)
                tv.visibility = View.VISIBLE
                et.hint = ""
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(et, 0)
            } else if(!hasFocus && et.text.isNotEmpty()){
                tv.text = text
                et.setPadding(50, 22, 50, 0)
            } else {
                tv.visibility = View.GONE
                et.hint = text
                et.setPadding(50, 18, 50, 18)
            }
        }
    }

    private fun etWatcher(et: EditText, bool: Boolean, iv: ImageView) {
        et.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //비밀번호 조건 충족 요건 설정해야함
                if(bool) {
                   iv.visibility = View.VISIBLE
                } else {
                    iv.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //비밀번호 조건 충족 요건 설정해야함
                if(bool) {
                    iv.visibility = View.VISIBLE
                } else {
                    iv.visibility = View.GONE
                }
            }
        })
    }



    private fun View.setMarginTop(topMargin: Int) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin)
        layoutParams = params
    }

}