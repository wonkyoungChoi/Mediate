package com.wk.mediate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wk.mediate.databinding.FragmentRegisterBinding
import com.wk.mediate.databinding.FragmentRegisterTutorInfoBinding

class RegisterTutorInfoFragment : Fragment() {
    private var _viewBinding: FragmentRegisterTutorInfoBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentRegisterTutorInfoBinding.inflate(inflater, container, false)


        return viewBinding.root
    }
}