package com.wk.mediate.ui.Register.Select.School.Search

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentSearchSchoolBinding
import com.wk.mediate.extensions.afterTextChanged
import com.wk.mediate.ui.Register.Select.School.SelectInfoViewModel
import com.wk.mediate.ui.Register.Select.SelectRegisterInfo


class SearchSchoolFragment : ViewBindingFragment<FragmentSearchSchoolBinding>() {
    private lateinit var model : SelectInfoViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this).get(SelectInfoViewModel::class.java)

        typeShow(SelectRegisterInfo.type)

        binding?.btNextActive?.setOnClickListener {
            popBackStack()
        }
    }

    private fun typeShow(type: String) {
        binding?.run {
            if(type == "TUTOR") {
                autoCompleteTextViewSchool.hint = getString(com.wk.mediate.R.string.select_info_now_univ)
                model.loadUniversity("")
                observeUniversitySearchResult()
            } else {
                autoCompleteTextViewSchool.hint = getString(com.wk.mediate.R.string.select_info_now_school)
                model.loadSchool("")
                observeSchoolSearchResult()
            }
            autoCompleteTextViewSchool.afterTextChanged {
                binding?.run {
                    if (type == "TUTOR") {
                        model.loadUniversity(autoCompleteTextViewSchool.text.toString())
                    } else {
                        model.loadSchool(autoCompleteTextViewSchool.text.toString())
                    }
                    if(autoCompleteTextViewSchool.text.equals("") && autoCompleteTextViewSchool.text == null) {
                        btNextActive.visibility = View.INVISIBLE
                        btNextInactive.visibility = View.VISIBLE
                    } else {
                        btNextActive.visibility = View.VISIBLE
                        btNextInactive.visibility = View.INVISIBLE
                    }
                }
            }

        }
    }

    private fun observeSchoolSearchResult() {
        model.getSchoolResult().observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, it)
            binding?.autoCompleteTextViewSchool?.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeUniversitySearchResult() {
        model.getUniversityResult().observe(viewLifecycleOwner, {
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, it)
            binding?.autoCompleteTextViewSchool?.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }
}

