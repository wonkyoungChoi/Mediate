package com.wk.mediate.ui.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wk.mediate.databinding.FragmentRegisterBinding
import com.wk.mediate.databinding.FragmentRegisterCodeBinding

class RegisterCodeFragment : Fragment() {
    private var _viewBinding: FragmentRegisterCodeBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentRegisterCodeBinding.inflate(inflater, container, false)


        return viewBinding.root
    }
}