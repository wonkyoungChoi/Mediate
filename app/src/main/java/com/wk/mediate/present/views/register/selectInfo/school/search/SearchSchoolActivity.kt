package com.wk.mediate.present.views.register.selectInfo.school.search

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.R
import com.wk.mediate.data.models.SelectRegisterInfo
import com.wk.mediate.present.config.ViewBindingActivity
import com.wk.mediate.databinding.ActivitySearchSchoolBinding
import com.wk.mediate.present.config.LoadingProgress
import com.wk.mediate.present.extension.afterTextChanged
import com.wk.mediate.present.extension.setAlphaClickable
import com.wk.mediate.present.utils.DataHandler
import com.wk.mediate.present.views.register.selectInfo.school.SearchInfoViewModel

class SearchSchoolActivity : ViewBindingActivity<ActivitySearchSchoolBinding>() {
    private lateinit var model : SearchInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(this).get(SearchInfoViewModel::class.java)

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
                autoCompleteTextViewSchool.hint = getString(R.string.select_info_now_univ)
                model.getLivedataUnivResult("")
                observeSearchResult()
            } else {
                autoCompleteTextViewSchool.hint = getString(R.string.select_info_now_school)
                model.getLivedataSchoolResult("")
                observeSearchResult()
            }
            autoCompleteTextViewSchool.afterTextChanged {
                binding?.run {
                    if (type == "TUTOR") {
                        model.getLivedataUnivResult(autoCompleteTextViewSchool.text.toString())
                    } else {
                        model.getLivedataSchoolResult(autoCompleteTextViewSchool.text.toString())
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

    private fun observeSearchResult() {
        model.searchResult.observe(this) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    LoadingProgress.shared.dismiss()
                    if(dataHandler.data != null) {
                        binding?.run {
                            val adapter = ArrayAdapter(this@SearchSchoolActivity, R.layout.support_simple_spinner_dropdown_item, dataHandler.data.result)
                            binding?.autoCompleteTextViewSchool?.setAdapter(adapter)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                is DataHandler.ERROR -> {
                    LoadingProgress.shared.dismiss()
                }
                is DataHandler.LOADING -> {
                    LoadingProgress.shared.show(this@SearchSchoolActivity)
                }
                else -> {}
            }

        }
    }

}

