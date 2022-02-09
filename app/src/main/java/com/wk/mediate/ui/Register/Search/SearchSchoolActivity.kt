package com.wk.mediate.ui.Register.Search

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.databinding.ActivitySearchSchoolBinding
import com.wk.mediate.ui.Login.Login
import com.wk.mediate.ui.Login.LoginViewModel

class SearchSchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchSchoolBinding
    private lateinit var model : SearchSchoolViewModel
    private lateinit var items: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchSchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(SearchSchoolViewModel::class.java)
        model.loadSchool("")
        observeSearchResult()
        textWatcher()


    }

    private fun observeSearchResult() {
        model.getSchoolResult().observe(this, {
            Log.d("Observe", "Obeserve")
            val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, it)
            binding.autoCompleteTextView.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }

    private fun textWatcher() {
        binding.autoCompleteTextView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.loadSchool(binding.autoCompleteTextView.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }
}