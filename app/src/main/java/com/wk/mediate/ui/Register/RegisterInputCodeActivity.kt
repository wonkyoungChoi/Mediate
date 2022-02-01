package com.wk.mediate.ui.Register

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.databinding.ActivityInputCodeBinding

class RegisterInputCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputCodeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        focusableCode()

    }

    private fun focusableCode() {
        Log.d("test","test")
        binding.etCode.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                Log.d("test","test")
                binding.etCode.setPadding(50, 19, 50, 0)
                binding.tvHintInputCode.visibility = View.VISIBLE
                binding.etCode.hint = ""
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.etCode, 0)
            }
        }
    }

    private fun checkInput() {
        //입력하면 다음 버튼 활성화
    }


}