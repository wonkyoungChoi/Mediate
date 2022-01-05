package com.wk.mediate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wk.mediate.databinding.ActivityLoginBinding
import com.wk.mediate.databinding.ActivityRegisterMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {
            val intent = Intent(it.context, RegisterMainActivity::class.java)
            it.context.startActivity(intent)
        }

    }
}