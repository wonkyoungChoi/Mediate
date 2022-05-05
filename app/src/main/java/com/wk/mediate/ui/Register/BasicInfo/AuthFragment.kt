package com.wk.mediate.ui.Register.BasicInfo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.wk.mediate.R
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentAuthBinding
import com.wk.mediate.extensions.focusEditTextChange
import com.wk.mediate.extensions.setAlphaClickable
import com.wk.mediate.extensions.util.Delay
import java.util.concurrent.TimeUnit

class AuthFragment : ViewBindingFragment<FragmentAuthBinding>() {
    private lateinit var timer : CountDownTimer
    private lateinit var phone : String
    private lateinit var verificationId : String
    private var check : Boolean = true
    private var agreementCheck : Boolean = false
    private var checkNum : Int = 0
    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            etName.focusEditTextChange(requireActivity(), tvHintName, getString(R.string.name))
            etPhoneNum.focusEditTextChange(requireActivity(), tvHintPhoneNum, getString(R.string.phone_num))

            tvSendAuth.setAlphaClickable(true)
            tvResendAuth.setAlphaClickable(true)
            btAuthCheckActive.setAlphaClickable(true)
            btNextActive.setAlphaClickable(true)

            authCodeTimer()

            checkboxSet(checkbox1)
            checkboxSet(checkbox2)
            checkboxSet(checkbox3)
            checkboxSet(checkbox4)

            onClick()
        }
    }

    private fun onClick() {
        binding?.run {
            //체크박스 전체동의 클릭
            checkboxAll.setOnClickListener {
                if(checkboxAll.isChecked) {
                    checkboxAllSet(true)
                } else {
                    checkboxAllSet(false)
                }
            }

            //인증번호 보내기 클릭
            tvSendAuth.setOnClickListener{
                sendAuth(etName, etPhoneNum, agreementCheck, it.context)
            }

            //인증번호 재전송 클릭
            tvResendAuth.setOnClickListener{
                sendAuth(etName, etPhoneNum, agreementCheck, it.context)
            }

            //인증번호 체크 클릭
            btAuthCheckActive.setOnClickListener {
                verifyCode(etInputAuthActive.text.toString())
            }


            //다음버튼 클릭
            btNextActive.setOnClickListener {
                if(check) {
                    BasicRegisterInfo.info.name = etName.text.toString()
                    BasicRegisterInfo.info.phoneNum = etPhoneNum.text.toString()
                    val fragment = InputCodeFragment()
                    pushFragment(fragment)
                } else {
                    Toast.makeText(requireContext(), "인증을 진행해주세요.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun checkboxSet(checkBox: CheckBox) {
        binding?.run {
            checkBox.setOnClickListener {
                if(checkBox.isChecked) {
                    checkBox.isChecked = true
                    checkNum++
                    if(checkNum == 4) {
                        agreementCheck = true
                        checkboxAll.isChecked = true
                    }
                } else {
                    checkboxAll.isChecked = false
                    agreementCheck = false
                    checkBox.isChecked = false
                    checkNum--

                }
            }
        }
    }

    private fun checkboxAllSet(boolean: Boolean) {
        binding?.run {
            checkbox1.isChecked = boolean
            checkbox2.isChecked = boolean
            checkbox3.isChecked = boolean
            checkbox4.isChecked = boolean
        }
        agreementCheck = boolean
    }

    private fun sendAuth(et_name: EditText, et_phoneNum: EditText, agreement: Boolean, context: Context) {
        if (et_name.text.isNotEmpty() && et_phoneNum.length() > 9 && agreement) {
            phone = "+82" + et_phoneNum.text.substring(1)

            binding?.run {
                tvSendAuth.visibility = View.GONE
                tvResendAuth.visibility = View.VISIBLE
                etInputAuthActive.visibility = View.VISIBLE
                etInputAuthInactive.visibility = View.INVISIBLE
                btAuthCheckInactive.visibility = View.GONE
                btAuthCheckActive.visibility = View.VISIBLE

                btAuthCheckActive.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

                sendVerificationCode(phone)

                timer.cancel()
                timer.start()

                blockTouch(400)

                etInputAuthActive.focusEditTextChange(requireActivity(), tvHintInputAuth, getString(R.string.input_auth))

                Toast.makeText(context, "인증번호가 전송되었습니다. 2분 이내에 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

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
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val code = credential.smsCode
            if (code != null) {
                binding?.etInputAuthActive?.setText(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {

            // Invalid request
            // The SMS quota for the project has been exceeded
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()

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
                .addOnCompleteListener(requireActivity()) { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {

                        check = true
                        timer.cancel()

                        binding?.run {
                            btNextActive.visibility = View.VISIBLE
                            btNextInactive.visibility = View.GONE
                            btAuthCheckActive.isClickable = false
                            tvTimeLimit.visibility = View.GONE
                        }

                        Toast.makeText(requireContext(), "인증을 완료하였습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), task.exception!!.message, Toast.LENGTH_SHORT)
                                .show()
                    }
                }
    }


    private fun authCodeTimer() {
        val mnMilliSecond = 1000
        val mnExitDelay = 120
        val delay = mnExitDelay * mnMilliSecond
        timer = object : CountDownTimer(delay.toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(l: Long) {
                var k = l / 1000
                binding?.run {
                    tvTimeLimit.visibility = View.VISIBLE
                    when (k) {
                        120L -> tvTimeLimit.text = "02 : 00"
                        in 70..119 -> {
                            k %= 60
                            tvTimeLimit.text = "01 : $k"
                        }
                        in 61..69 -> {
                            k %= 60
                            tvTimeLimit.text = "01 : 0$k"
                        }
                        60L -> tvTimeLimit.text = "01 : 00"
                        in 11..59 -> {
                            tvTimeLimit.text = "00 : $k"
                        }
                        else -> tvTimeLimit.text = ("00 : 0$k")
                    }
                }
            }

            override fun onFinish() {
                binding?.run {
                    tvTimeLimit.text = ("")
                    tvTimeLimit.visibility = (View.GONE)
                }

                if (false.also { check = it }) {
                    Toast.makeText(requireContext(), "제한시간 종료", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

