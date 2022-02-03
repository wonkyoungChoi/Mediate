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
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.databinding.ActivityInputCodeBinding

class InputCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputCodeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextWatcher()
        focusableCode()

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

    private fun focusableCode() {
        Log.d("test","test")
        binding.etCode.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                Log.d("test","test")
                binding.etCode.setPadding(50, 22, 50, 0)
                binding.tvHintInputCode.visibility = View.VISIBLE
                binding.etCode.hint = ""
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.etCode, 0)
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