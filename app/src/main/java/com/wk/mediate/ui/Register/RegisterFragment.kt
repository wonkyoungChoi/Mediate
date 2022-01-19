package com.wk.mediate.Register

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.wk.mediate.databinding.FragmentRegisterBinding
import java.util.concurrent.TimeUnit

class RegisterFragment : Fragment(){
    private var _viewBinding: FragmentRegisterBinding? = null
    private val binding get() = _viewBinding!!
    private lateinit var timer : CountDownTimer
    private lateinit var phone : String
    private lateinit var verificationId : String
    private var check : Boolean = false
    private val mAuth :FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        

        return binding.root
    }





}