package com.andy.rios.elektra.ui.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andy.rios.elektra.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet() : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetBinding

    var takePicture : () -> Unit = {}
    var uploadFile : () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contTakeImage.setOnClickListener {
            takePicture.invoke()
        }

        binding.contUploadFile.setOnClickListener {
            uploadFile.invoke()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}
