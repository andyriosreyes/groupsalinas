package com.andy.rios.elektra.base

import androidx.fragment.app.Fragment
import com.andy.rios.elektra.ui.views.dialogs.closeProgressDialog
import com.andy.rios.elektra.ui.views.dialogs.showProgressDialog

open class BaseFragment() : Fragment() {

    var isProgressDialogOnScreen: Boolean = false

    val baseActivity: BaseActivity by lazy {
        activity as BaseActivity
    }

    fun showProgress() {
        isProgressDialogOnScreen = true
        baseActivity.showProgressDialog()
    }

    fun dismissProgress() {
        if (!baseActivity.isFinishing) {
            isProgressDialogOnScreen = false
            baseActivity.closeProgressDialog()
        }
    }
}