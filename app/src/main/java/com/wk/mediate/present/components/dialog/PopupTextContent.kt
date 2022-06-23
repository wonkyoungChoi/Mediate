package com.wk.mediate.present.components.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.wk.mediate.databinding.ViewPopupTextContentBinding

class PopupTextContent: FrameLayout {
    lateinit var binding: ViewPopupTextContentBinding
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        binding = ViewPopupTextContentBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }

    fun setting(content: String) {
        binding.textContent.text = content
    }
}