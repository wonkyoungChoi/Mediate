package com.wk.mediate.ui.Register.SelectInfo.School

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.R
import com.wk.mediate.base.ViewBindingFragment
import com.wk.mediate.databinding.FragmentSelectSchoolInfoBinding
import com.wk.mediate.extensions.afterTextChanged
import com.wk.mediate.extensions.setAlphaClickable
import com.wk.mediate.ui.Register.BasicInfo.BasicRegisterInfo
import com.wk.mediate.ui.Register.SelectInfo.School.Search.SearchSchoolActivity
import com.wk.mediate.ui.Register.SelectInfo.SelectAreaFragment
import com.wk.mediate.ui.Register.SelectInfo.SelectRegisterInfo


class SelectSchoolInfoFragment : ViewBindingFragment<FragmentSelectSchoolInfoBinding>() {
    private lateinit var model : SelectInfoViewModel

    override fun onResume() {
        binding?.run {
            if(SelectRegisterInfo.type == "TUTOR") {
                if (SelectRegisterInfo.tutorInfo.school != "") {
                    tvSchool.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    tvSchool.text = SelectRegisterInfo.tutorInfo.school
                }
            } else {
                if (SelectRegisterInfo.tuteeInfo.school != "") {
                    tvSchool.text = SelectRegisterInfo.tuteeInfo.school
                }
            }
        }
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this).get(SelectInfoViewModel::class.java)

        typeShow(SelectRegisterInfo.type)

        binding?.run {
            btNextActive.setAlphaClickable(true)
            btBack.setAlphaClickable(true)

            tvName.text = BasicRegisterInfo.info.name

            if(SelectRegisterInfo.type == "TUTOR") {
                tvTutInfo.text = getString(R.string.tutor)
            } else {
                tvTutInfo.text = getString(R.string.tutee)
            }

            btNextActive.setOnClickListener {
                if(SelectRegisterInfo.type == "TUTOR") {
                    SelectRegisterInfo.tutorInfo.major = etMajor.text.toString()
                }
                val fragment = SelectAreaFragment()
                replaceNextFragment(fragment)
            }

            btBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun typeShow(type: String?) {
        binding?.run {
            if(type == "TUTOR") {
                //튜터일 때
                tvTitle.text = getString(R.string.select_info_univ)
                tvSchool.text = getString(R.string.select_info_now_univ)
                etMajor.hint = "학과"
                //학과 TextWatcher
                etMajor.afterTextChanged {
                    binding?.run {
                        if(etMajor.text.isNotEmpty() && SelectRegisterInfo.tutorInfo.school != "" && spinnerGrade.isNotEmpty()) {
                            btNextActive.visibility = View.VISIBLE
                            btNextInactive.visibility = View.INVISIBLE
                        } else {
                            btNextActive.visibility = View.INVISIBLE
                            btNextInactive.visibility = View.VISIBLE
                        }
                    }
                }
            } else {
                //튜티일 때
                tvTitle.text = getString(R.string.select_info_school)
                tvSchool.text = getString(R.string.select_info_now_school)
                etMajor.visibility = View.GONE
            }
            clickTextViewSchool()
            initGradeSpinner(type)
        }
    }

    //학교 선택 TextView 클릭
    private fun clickTextViewSchool() {
        binding?.tvSchool?.setOnClickListener {
            val activity = SearchSchoolActivity()
            addActivity(requireActivity(), activity)
        }
    }

    //학년 Spinner
    private fun initGradeSpinner(type: String?) {
        val list = if(type == "TUTOR") {
            resources.getStringArray(R.array.univ_grade)
        } else {
            resources.getStringArray(R.array.school_grade)
        }
        val spinnerAdapter= object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, list) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
            ): View {
                val view: TextView =
                        super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if (position == 0) {
                    view.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
                } else {
                    //here it is possible to define color for other items by
                    view.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                }
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        binding?.run {
            spinnerGrade.adapter = spinnerAdapter

            spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    if(pos == 0) (spinnerGrade.selectedView as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
                    else {
                        (spinnerGrade.selectedView as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                        if(type == "TUTOR") {
                            SelectRegisterInfo.tutorInfo.grade = (spinnerGrade.selectedView as TextView).text.toString()
                        } else {
                            SelectRegisterInfo.tuteeInfo.grade = (spinnerGrade.selectedView as TextView).text.toString()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }
}

