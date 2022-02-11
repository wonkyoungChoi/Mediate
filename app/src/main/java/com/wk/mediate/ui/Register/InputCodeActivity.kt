package com.wk.mediate.ui.Register

import android.app.Activity
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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.R
import com.wk.mediate.databinding.ActivityInputCodeBinding

class InputCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputCodeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextWatcher()
        focusableEditText(binding.etCode, binding.tvHintInputCode, getString(R.string.input_code_hint))

        binding.btNextActive.setOnClickListener {
            //코드 검증 해야함
            if(true) {
            val intent = Intent(this, InputIdPasswordActivity::class.java)
            startActivity(intent)
            finish()
            } else {
                binding.tvInputCodeCheck.visibility = View.VISIBLE
                binding.tvCodeInfo.setMarginTop(70)
            }

        }

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

    private fun editTextWatcher() {
        binding.etCode.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btNextActive.visibility = View.VISIBLE
                binding.btNextInactive.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.etCode.text.isEmpty()) {
                    binding.btNextActive.visibility = View.GONE
                    binding.btNextInactive.visibility = View.VISIBLE
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