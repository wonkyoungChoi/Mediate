package com.wk.mediate.present.config

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.wk.mediate.R
import com.wk.mediate.present.components.dialog.ButtonCallback

open class BaseFragment: Fragment() {
    var transition: Transition = Transition.None

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
    }

    val baseActivity: BaseActivity
        get() = requireActivity() as BaseActivity

    fun addActivity(firstActivity: Activity, secondActivity: Activity, transition: Transition = Transition.Push) {
        baseActivity.addActivity(firstActivity, secondActivity, transition)
    }

    fun modalFragment(firstActivity: Activity, secondActivity: Activity) {
        baseActivity.modalActivity(firstActivity, secondActivity)
    }

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


    fun showAlert(content: String,
                  confirmTitle:String? = null,
                  confirmCallback: ButtonCallback? = null) {
        baseActivity.showAlert(content, confirmTitle, confirmCallback)
    }
    fun showAlert(title: String? = null,
                  content: String,
                  confirmTitle:String? = null,
                  confirmCallback: ButtonCallback? = null) {
        baseActivity.showAlert(title, content, confirmTitle, confirmCallback)
    }

    fun showConfirm(content: String,
                    confirmTitle: String? = null,
                    confirmCallback: ButtonCallback? = null,
                    cancelTitle: String? = null,
                    cancelCallback: ButtonCallback? = null) {
        baseActivity.showConfirm(content, confirmTitle, confirmCallback, cancelTitle, cancelCallback)
    }

    fun showConfirm(title: String? = null,
                    content: String,
                    confirmTitle: String? = null,
                    confirmCallback: ButtonCallback? = null,
                    cancelTitle: String? = null,
                    cancelCallback: ButtonCallback? = null) {
        baseActivity.showConfirm(title, content, confirmTitle, confirmCallback, cancelTitle, cancelCallback)
    }

    fun showToast(message: String) {
        baseActivity.showToast(message)
    }

    open fun blockTouch(delay: Long = 400) {
        baseActivity.blockTouch(delay)
    }

    open fun moveToRoot() {
        baseActivity.moveToRoot()
    }
}