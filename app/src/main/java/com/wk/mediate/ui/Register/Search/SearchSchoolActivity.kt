package com.wk.mediate.ui.Register.Search

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.databinding.ActivitySearchSchoolBinding


class SearchSchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchSchoolBinding
    private lateinit var model : SearchSchoolViewModel
    private lateinit var items: ArrayList<String>
//    private val name : String? = intent.getStringExtra("name")
//    private val phoneNum : String? = intent.getStringExtra("phoneNum")
    private val type : String = "tutor"
        //intent.getStringExtra("type")!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchSchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(SearchSchoolViewModel::class.java)

        typeShow(type)
    }

    private fun typeShow(type: String) {
        if(type == "tutor") {
            binding.autoCompleteTextViewSchool.hint = "재학중인 대학교"
            binding.autoCompleteTextViewMajor.hint = "학과"
            model.loadUniversity("")
            model.loadMajor("")
            observeUniversitySearchResult()
            observeMajorSearchResult()
            majorTextWatcher()
        } else {
            binding.autoCompleteTextViewSchool.hint = "재학중인 학교"
            binding.autoCompleteTextViewMajor.visibility = View.GONE
            model.loadSchool("")
            observeSchoolSearchResult()
        }

        schoolTextWatcher(type)
        initGradeSpinner(type)

    }

    private fun observeSchoolSearchResult() {
        model.getSchoolResult().observe(this, {
            Log.d("Observe", "Obeserve")
            val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, it)
            binding.autoCompleteTextViewSchool.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeUniversitySearchResult() {
        model.getUniversityResult().observe(this, {
            Log.d("Observe", "Obeserve")
            val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, it)
            binding.autoCompleteTextViewSchool.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }

    private fun observeMajorSearchResult() {
        model.getMajorResult().observe(this, {
            Log.d("Observe", it[0])
            val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, it)
            binding.autoCompleteTextViewMajor.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }

    private fun schoolTextWatcher(type: String) {
        binding.autoCompleteTextViewSchool.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (type == "tutor") {
                    model.loadUniversity(binding.autoCompleteTextViewSchool.text.toString())
                } else {
                    model.loadSchool(binding.autoCompleteTextViewSchool.text.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun majorTextWatcher() {
        binding.autoCompleteTextViewMajor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.loadMajor(binding.autoCompleteTextViewMajor.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun initGradeSpinner(type: String) {
        val list = if(type == "tutor") {
            resources.getStringArray(com.wk.mediate.R.array.univ_grade)
        } else {
            resources.getStringArray(com.wk.mediate.R.array.school_grade)
        }
        val spinnerAdapter= object : ArrayAdapter<String>(this, com.wk.mediate.R.layout.spinner_item, list) {

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
                    view.setTextColor(ContextCompat.getColor(applicationContext, com.wk.mediate.R.color.text_grey))
                } else {
                    //here it is possible to define color for other items by
                    view.setTextColor(ContextCompat.getColor(applicationContext, com.wk.mediate.R.color.text_black))
                }
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(com.wk.mediate.R.layout.spinner_item)
        binding.spinnerGrade.adapter = spinnerAdapter

        binding.spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                if(pos == 0) (binding.spinnerGrade.selectedView as TextView).setTextColor(ContextCompat.getColor(applicationContext, com.wk.mediate.R.color.text_grey))
                 else (binding.spinnerGrade.selectedView as TextView).setTextColor(ContextCompat.getColor(applicationContext, com.wk.mediate.R.color.text_black))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }
}

