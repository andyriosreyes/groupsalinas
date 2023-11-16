package com.andy.rios.elektra.ui.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.andy.rios.elektra.R
import com.andy.rios.elektra.databinding.DialogGenericBinding
import com.andy.rios.elektra.ui.model.DialogModel

class GenericDialog : DialogFragment(){

    lateinit var dialogModel: DialogModel
    lateinit var fActivity : FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return inflater.inflate(R.layout.dialog_generic, container, false)
    }

    override fun onStart() {
        super.onStart()
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        fActivity = activity ?: return super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(fActivity)
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawableResource(R.drawable.white_bg)
        }

        if(::dialogModel.isInitialized && ::fActivity.isInitialized){
            val binding = DialogGenericBinding.inflate(fActivity.layoutInflater)
            if(dialogModel.icono == 0){
                dialogModel.icono = R.drawable.ic_warning
            }

            binding.ivDialogClose.visibility = if(dialogModel.close) View.VISIBLE else View.GONE

            binding.ivImage.setImageDrawable(VectorDrawableCompat.create(binding.ivImage.resources, dialogModel.icono, null))

            if(dialogModel.subtitle == ""){
                binding.tvDialogSubTitle.visibility = View.GONE
            }else {
                binding.tvDialogSubTitle.text = dialogModel.subtitle
            }

            requireContext().resources.displayMetrics.density
            requireContext().resources.displayMetrics.widthPixels

            binding.ivDialogClose.setOnClickListener {
                dismiss()
            }

            builder.setView(binding.root)
        } else
            dismiss()

        return builder.create()
    }
}

fun AppCompatActivity.showDialogGeneric(model: DialogModel, paramEventId: Int = 0): GenericDialog {
    val dialog = getDialogGeneric(model, paramEventId)
    dialog.dialogModel = model
    dialog.show(this.supportFragmentManager, GenericDialog::class.java.name)
    return dialog
}

fun AppCompatActivity.getDialogGeneric(model: DialogModel, paramEventId: Int = 0): GenericDialog {
    val dialog = GenericDialog()
    dialog.dialogModel = model
    return dialog
}