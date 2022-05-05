package com.wk.mediate.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.wk.mediate.R

open class BaseFragment: Fragment() {
    var transition: Transition = Transition.None

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
    }

    val baseActivity: BaseActivity
        get() = requireActivity() as BaseActivity

    fun pushFragment(fragment: Fragment, container: Int = R.id.fragment_container) {
        baseActivity.pushFragment(fragment, container)
    }

    fun modalFragment(fragment: Fragment, container: Int = R.id.fragment_container) {
        baseActivity.modelFragment(fragment, container)
    }

    fun popBackStack() {
        baseActivity.popBackStack()
    }

    fun replaceFragment(fragment: Fragment, container: Int = R.id.fragment_container) {
        baseActivity.replaceFragment(fragment, container)
    }

    fun replaceNextFragment(fragment: Fragment, container: Int = R.id.fragment_container) {
        baseActivity.replaceNextFragment(fragment, container)
    }

    fun replacePrevFragment(fragment: Fragment, container: Int = R.id.fragment_container) {
        baseActivity.replacePrevFragment(fragment, container)
    }

    fun registKeyboardHeightListener(listener: KeyboardHeightListener) {
        baseActivity.registKeyboardHeightListener(listener)
    }

    fun unregistKeyboardHeightListener(listener: KeyboardHeightListener) {
        baseActivity.unregistKeyboardHeightListener(listener)
    }
    fun setTouchHideKeyboard(view: View) {
        baseActivity.setTouchHideKeyboard(view)
    }

    fun hideKeyBoard(activity: Activity) {
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }

        (activity as BaseActivity).hideKeyBoard(activity)
    }

    fun showKeyboard(ed: EditText) {
        (activity as BaseActivity).showKeyboard(ed)
    }

    open fun blockTouch(delay: Long = 400) {
        baseActivity.blockTouch(delay)
    }

    open fun moveToRoot() {
        baseActivity.moveToRoot()
    }
}