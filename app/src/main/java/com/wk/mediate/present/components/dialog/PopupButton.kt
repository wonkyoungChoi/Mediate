package com.wk.mediate.present.components.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.wk.mediate.databinding.ViewPopupButtonBinding

typealias ButtonCallback = () -> Unit
class PopupButton: FrameLayout {
    lateinit var binding: ViewPopupButtonBinding
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
        binding = ViewPopupButtonBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }

    fun setting(dialog: PopupCombine, confirmTitle:String? = null, confirmCallback:ButtonCallback? = null, cancelTitle:String? = null, cancelCallback: ButtonCallback? = null ) {
        if( confirmTitle != null ) {
            binding.buttonPossitive.text = confirmTitle
        }
        binding.buttonPossitive.setOnClickListener {
            confirmCallback?.invoke()
            dialog.dismiss()
        }

        if( cancelTitle != null ) {
            binding.buttonNegative.text = cancelTitle
        }
        if( cancelCallback != null ) {
            binding.buttonNegative.setOnClickListener {
                cancelCallback.invoke()
                dialog.dismiss()
            }
            binding.buttonNegative.visibility = View.VISIBLE
        } else {
            binding.buttonNegative.visibility = View.GONE
        }
    }
}