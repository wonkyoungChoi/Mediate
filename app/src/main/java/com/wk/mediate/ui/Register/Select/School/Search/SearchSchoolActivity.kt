package com.wk.mediate.ui.Register.Select.School.Search

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.databinding.ActivitySearchSchoolBinding
import com.wk.mediate.ui.Register.Select.School.SelectInfoViewModel


class SearchSchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchSchoolBinding
    private lateinit var model : SelectInfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchSchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type").toString()

        model = ViewModelProvider(this).get(SelectInfoViewModel::class.java)

        typeShow(type)

    }

    private fun typeShow(type: String) {
        if(type == "tutor") {
            binding.autoCompleteTextViewSchool.hint = getString(com.wk.mediate.R.string.select_info_now_univ)
            model.loadUniversity("")
            observeUniversitySearchResult()
        } else {
            binding.autoCompleteTextViewSchool.hint = getString(com.wk.mediate.R.string.select_info_now_school)
            model.loadSchool("")
            observeSchoolSearchResult()
        }
        schoolTextWatcher(type)
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
}

