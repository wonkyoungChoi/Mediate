package com.wk.mediate.ui.Register.Select.Subject

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wk.mediate.databinding.ActivityInputSubjectBinding

class SelectSubjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputSubjectBinding
    private lateinit var model : SelectSubjectViewModel
    private lateinit var adapter: SelectSubjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(SelectSubjectViewModel::class.java)
        model.loadSubject("")
        subjectTextWatcher()
        observeSubjectSearchResult()


    }

    //과목 TextWatcher
    private fun subjectTextWatcher() {
        binding.autoCompleteTextViewSubject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.loadSubject(binding.autoCompleteTextViewSubject.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.autoCompleteTextViewSubject.setOnItemClickListener { _, _, position, _ ->
            if(position == 0) binding.autoCompleteTextViewSubject.text = null
            else {

            }
        }
    }

    //과목 관련 LiveData 관찰
    private fun observeSubjectSearchResult() {
        model.getSubjectResult().observe(this, {
            adapter = SelectSubjectAdapter(this, R.layout.simple_dropdown_item_1line)
            adapter.setResult(it)
            binding.autoCompleteTextViewSubject.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })
    }
}