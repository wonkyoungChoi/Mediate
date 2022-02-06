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
import com.wk.mediate.R
import com.wk.mediate.databinding.ActivityInputIdPasswordBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class InputIdPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputIdPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputIdPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //editTextWatcher()
        focusableEditText(binding.etId, binding.tvHintInputId, getString(R.string.input_id_hint),"아이디")
        focusableEditText(binding.etPassword, binding.tvHintInputPassword, getString(R.string.input_password_hint),"비밀번호")
        focusableEditText(binding.etPasswordCheck, binding.tvHintInputPasswordCheck, getString(R.string.input_password_hint), "비밀번호 재입력")

//        etPasswordWatcher(binding.etPassword, binding.passwordCheck)
//        etPasswordCheckWatcher(binding.etPasswordCheck, binding.passwordReCheck)

        binding.btNextActive.setOnClickListener {
            val intent = Intent(this, SelectTypeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun focusableEditText(et: EditText, tv: TextView, focusText: String, unFocusText: String) {
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
                tv.text = unFocusText
                et.setPadding(50, 22, 50, 0)
            } else {
                tv.visibility = View.GONE
                et.hint = unFocusText
                et.setPadding(50, 18, 50, 18)
            }
        }
    }

    private fun etPasswordWatcher(et: EditText, bool: Boolean, iv: ImageView) {
        et.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(bool) {
                   iv.visibility = View.VISIBLE
                } else {
                    iv.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun etPasswordCheckWatcher(et: EditText, iv: ImageView) {
        et.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(passwordCheckValidation(binding.etPassword.text.toString(), binding.etPasswordCheck.text.toString())) {
                    iv.visibility = View.VISIBLE
                } else {
                    iv.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun passwordValidation(password: String): Boolean {
        Log.d("validation", "validation")
        val valSymbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";
        val valAlpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";

        val patternSymbol : Pattern  = Pattern.compile(valSymbol)
        val patternAlpha : Pattern = Pattern.compile(valAlpha);

        val matcherSymbol : Matcher = patternSymbol.matcher(password);
        val matcherAlpha : Matcher = patternAlpha.matcher(password);

        return matcherSymbol.find() && matcherAlpha.find()
    }

    private fun passwordCheckValidation(password: String, passwordCheck: String): Boolean {
        return password == passwordCheck
    }

    private fun View.setMarginTop(topMargin: Int) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin)
        layoutParams = params
    }

}