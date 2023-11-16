package com.andy.rios.elektra.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andy.rios.elektra.ui.views.dialogs.closeProgressDialog
import com.andy.rios.elektra.ui.views.dialogs.showProgressDialog

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showProgress() {
        showProgressDialog()
    }

    fun dismissProgress() {
        if (!isFinishing) {
            closeProgressDialog()
        }
    }


}