package com.wk.mediate.ui.Register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.wk.mediate.databinding.ActivityRegisterAuthBinding
import java.util.concurrent.TimeUnit

class RegisterAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterAuthBinding
    private lateinit var timer : CountDownTimer
    private lateinit var phone : String
    private lateinit var verificationId : String
    private var check : Boolean = false
    private var agreementCheck : Boolean = false
    private var checkNum : Int = 0
    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authCodeTimer()

        binding.checkboxAll.setOnClickListener {
            if(binding.checkboxAll.isChecked) {
                checkboxAllSet(true)
            } else {
                checkboxAllSet(false)
            }
        }

        checkboxSet(binding.checkbox1)
        checkboxSet(binding.checkbox2)
        checkboxSet(binding.checkbox3)
        checkboxSet(binding.checkbox4)

        binding.tvSendAuth.setOnClickListener{
            sendAuth(binding.etName, binding.etPhoneNum, agreementCheck, it.context)
        }

        binding.tvResendAuth.setOnClickListener{
            sendAuth(binding.etName, binding.etPhoneNum, agreementCheck, it.context)
        }

        binding.btAuthCheckActive.setOnClickListener {
            verifyCode(binding.etInputAuthActive.text.toString())
        }

        binding.btNextActive.setOnClickListener {
            val intent = Intent(this, RegisterSelectTypeActivity::class.java)
            intent.putExtra("name", binding.etName.text.toString())
            startActivity(intent)
            finish()
        }

    }



    private fun checkboxSet(checkBox: CheckBox) {
        checkBox.setOnClickListener {
            if(checkBox.isChecked) {
                checkBox.isChecked = true
                checkNum++
                if(checkNum == 4) {
                    agreementCheck = true
                    binding.checkboxAll.isChecked = true
                }
            } else {
                binding.checkboxAll.isChecked = false
                agreementCheck = false
                checkBox.isChecked = false
                checkNum--

            }
            Log.d("checkNum", checkNum.toString())
            Log.d("boolean", agreementCheck.toString())
        }
    }

    private fun checkboxAllSet(boolean: Boolean) {
        binding.checkbox1.isChecked = boolean
        binding.checkbox2.isChecked = boolean
        binding.checkbox3.isChecked = boolean
        binding.checkbox4.isChecked = boolean
        agreementCheck = boolean
    }

    private fun sendAuth(et_name: EditText, et_phoneNum: EditText, agreement: Boolean, context: Context) {
        if (et_name.text.isNotEmpty() && et_phoneNum.length() > 9 && agreement) {
            phone = "+82" + et_phoneNum.text.substring(1)
            sendVerificationCode(phone)

            timer.cancel()
            timer.start()

            binding.tvSendAuth.visibility = View.GONE
            binding.tvResendAuth.visibility = View.VISIBLE
            binding.etInputAuthActive.visibility = View.VISIBLE
            binding.etInputAuthInactive.visibility = View.INVISIBLE
            binding.btAuthCheckInactive.visibility = View.GONE
            binding.btAuthCheckActive.visibility = View.VISIBLE


            Toast.makeText(context, "인증번호가 전송되었습니다. 2분 이내에 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else if(et_name.text.isEmpty()) {
            Toast.makeText(context, "이름을 정확히 입력하세요.", Toast.LENGTH_SHORT).show()
        } else if(et_phoneNum.length() <= 9){
            Toast.makeText(context, "휴대전화 번호를 정확히 입력하세요.", Toast.LENGTH_SHORT).show()
        }  else {
            Toast.makeText(context, "약관에 모두 동의해주세요.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun sendVerificationCode(number: String) {
        val options: PhoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val code = credential.smsCode
            if (code != null) {
                binding.etInputAuthActive.setText(code)
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {

            // Invalid request
            // The SMS quota for the project has been exceeded
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationid: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationid, token)
            verificationId = verificationid
        }
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                this,
                OnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {

                        check = true
                        timer.cancel()

                        binding.btNextActive.visibility = View.VISIBLE
                        binding.btNextInactive.visibility = View.GONE

                        Toast.makeText(applicationContext, "인증을 완료하였습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, task.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }


    private fun authCodeTimer() {
        val mnMilliSecond = 1000
        val mnExitDelay = 120
        val delay = mnExitDelay * mnMilliSecond
        timer = object : CountDownTimer(delay.toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(l: Long) {
                var k = l / 1000
                binding.tvTimeLimit.visibility = View.VISIBLE
                when (k) {
                    120L -> binding.tvTimeLimit.text = "02 : 00"
                    in 70..119 -> {
                        k %= 60
                        binding.tvTimeLimit.text = "01 : $k"
                    }
                    in 61..69 -> {
                        k %= 60
                        binding.tvTimeLimit.text = "01 : 0$k"
                    }
                    60L -> binding.tvTimeLimit.text = "01 : 00"
                    in 11..59 -> {
                        binding.tvTimeLimit.text = "00 : $k"
                    }
                    else -> binding.tvTimeLimit.text = ("00 : 0$k")
                }

            }

            override fun onFinish() {
                Log.d("FINISH", "FINISH")
                binding.tvTimeLimit.text = ("")
                binding.tvTimeLimit.visibility = (View.GONE)
                if (false.also { check = it }) {
                    Toast.makeText(applicationContext, "제한시간 종료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}