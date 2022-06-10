package com.wk.mediate.ui.Register.SelectInfo.School.Search

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.base.ViewBindingActivity
import com.wk.mediate.databinding.ActivitySearchSchoolBinding
import com.wk.mediate.extensions.afterTextChanged
import com.wk.mediate.extensions.setAlphaClickable
import com.wk.mediate.ui.Register.SelectInfo.School.SelectInfoViewModel
import com.wk.mediate.ui.Register.SelectInfo.SelectRegisterInfo


class SearchSchoolActivity : ViewBindingActivity<ActivitySearchSchoolBinding>() {
    private lateinit var model : SelectInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(this).get(SelectInfoViewModel::class.java)

        typeShow(SelectRegisterInfo.type)

        binding?.run {
            btBack.setAlphaClickable(true)
            btNextActive.setAlphaClickable(true)

            btBack.setOnClickListener {
                onBackPressed()
            }

            btNextActive.setOnClickListener {
                if (autoCompleteTextViewSchool.text.isNotEmpty()) {
                    if (SelectRegisterInfo.type == "TUTOR") {
                        SelectRegisterInfo.tutorInfo.school = autoCompleteTextViewSchool.text.toString()
                    } else {
                        SelectRegisterInfo.tuteeInfo.school = autoCompleteTextViewSchool.text.toString()
                    }
                    onBackPressed()
                } else {
                    showToast("학교를 선택해주세요.")
                }

            }
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
        model.getSchoolResult().observe(this, {
            val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, it)
            binding?.autoCompleteTextViewSchool?.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeUniversitySearchResult() {
        model.getUniversityResult().observe(this , {
            val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, it)
            binding?.autoCompleteTextViewSchool?.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }
}

