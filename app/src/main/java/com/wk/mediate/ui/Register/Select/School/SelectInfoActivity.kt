package com.wk.mediate.ui.Register.Select.School

import android.content.Intent
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
import com.wk.mediate.R
import com.wk.mediate.databinding.ActivitySelectInfoBinding
import com.wk.mediate.ui.Register.Select.School.Search.SearchSchoolActivity


class SelectInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectInfoBinding
    private lateinit var model : SelectInfoViewModel
    private lateinit var items: ArrayList<String>
    //    private val name : String? = intent.getStringExtra("name")
//    private val phoneNum : String? = intent.getStringExtra("phoneNum")
    private val type : String = "tutor"
    //intent.getStringExtra("type")!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(SelectInfoViewModel::class.java)

        typeShow(type)
    }

    private fun typeShow(type: String) {
        if(type == "tutor") {
            //튜터일 때
            binding.tvTitle.text = getString(R.string.select_info_univ)
            binding.tvSchool.text = getString(R.string.select_info_now_univ)
            binding.autoCompleteTextViewMajor.hint = "학과"
            model.loadMajor("")
            observeMajorSearchResult()
            majorTextWatcher()
        } else {
            //튜티일 때
            binding.tvTitle.text = getString(R.string.select_info_school)
            binding.tvSchool.text = getString(R.string.select_info_now_school)
            binding.autoCompleteTextViewMajor.visibility = View.GONE
        }
        clickTextViewSchool(type)
        initGradeSpinner(type)
    }

    //학교 선택 TextView 클릭
    private fun clickTextViewSchool(type: String) {
        binding.tvSchool.setOnClickListener {
            val intent = Intent(this, SearchSchoolActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }
    }

    //학과 관련 LiveData 관찰
    private fun observeMajorSearchResult() {
        model.getMajorResult().observe(this, {
            Log.d("Observe", it[0])
            val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, it)
            binding.autoCompleteTextViewMajor.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }

    //학과 TextWatcher
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

    //학년 Spinner
    private fun initGradeSpinner(type: String) {
        val list = if(type == "tutor") {
            resources.getStringArray(R.array.univ_grade)
        } else {
            resources.getStringArray(R.array.school_grade)
        }
        val spinnerAdapter= object : ArrayAdapter<String>(this, R.layout.spinner_item, list) {

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
                    view.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_grey))
                } else {
                    //here it is possible to define color for other items by
                    view.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_black))
                }
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        binding.spinnerGrade.adapter = spinnerAdapter

        binding.spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                if(pos == 0) (binding.spinnerGrade.selectedView as TextView).setTextColor(ContextCompat.getColor(applicationContext, R.color.text_grey))
                else (binding.spinnerGrade.selectedView as TextView).setTextColor(ContextCompat.getColor(applicationContext, R.color.text_black))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }
}

