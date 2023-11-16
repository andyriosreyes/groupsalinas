package com.andy.rios.elektra.ui.views.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.andy.rios.elektra.R
import com.andy.rios.elektra.databinding.DialogProgressBinding

class ProgressDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = this.activity ?: return super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(activity)
        val binding = DialogProgressBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog?.window != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog!!.window!!.setLayout(width, height)
        }
    }

}

fun FragmentActivity?.showProgressDialog() {
    val dialog = ProgressDialog()
    this?.let {
        dialog.isCancelable = false
        dialog.show(it.supportFragmentManager, ProgressDialog::class.java.name)
    }
}

fun FragmentActivity?.closeProgressDialog() {
    val progressDialog = this?.supportFragmentManager?.findFragmentByTag(ProgressDialog::class.java.name)
    if (progressDialog is ProgressDialog) {
        try {
            progressDialog.dismissAllowingStateLoss()
        } catch (ex: Exception) {}
    }
}