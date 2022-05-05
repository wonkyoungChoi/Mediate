package com.wk.mediate.extensions

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView

fun AutoCompleteTextView.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }
    })
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }
    })
}

fun EditText.focusEditTextChange(activity: Activity,tv: TextView, focusText: String, unFocusText: String? = null) {
    this.setOnFocusChangeListener { _, hasFocus ->

        if(hasFocus) {
            this.setPadding(40, 22, 40, 0)
            tv.text = focusText
            tv.visibility = View.VISIBLE
            this.hint = ""
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, 0)
        } else if(!hasFocus && this.text.isNotEmpty()){
            if(unFocusText == null) {
                tv.text = focusText
                this.setPadding(40, 22, 40, 0)
            } else {
                tv.text = unFocusText
                this.setPadding(40, 22, 40, 0)
            }
        } else {
            if(unFocusText == null) {
                tv.visibility = View.GONE
                this.hint = focusText
                this.setPadding(50, 18, 50, 18)
            } else {
                tv.visibility = View.GONE
                this.hint = unFocusText
                this.setPadding(50, 18, 50, 18)
            }
        }

    }
}

