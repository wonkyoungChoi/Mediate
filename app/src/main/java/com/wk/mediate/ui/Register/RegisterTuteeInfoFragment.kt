package com.wk.mediate.ui.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wk.mediate.databinding.FragmentRegisterBinding
import com.wk.mediate.databinding.FragmentRegisterTuteeInfoBinding
import com.wk.mediate.databinding.FragmentRegisterTutorInfoBinding

class RegisterTuteeInfoFragment : Fragment() {
    private var _viewBinding: FragmentRegisterTuteeInfoBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentRegisterTuteeInfoBinding.inflate(inflater, container, false)


        return viewBinding.root
    }
}