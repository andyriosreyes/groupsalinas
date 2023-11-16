package com.andy.rios.elektra.ui.views.register

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.andy.rios.elektra.R
import com.andy.rios.elektra.base.BaseFragment
import com.andy.rios.elektra.databinding.FragmentContactRegisterBinding
import com.andy.rios.elektra.ui.model.Contact
import com.andy.rios.elektra.ui.model.DialogModel
import com.andy.rios.elektra.ui.util.ModalBottomSheet
import com.andy.rios.elektra.ui.util.State
import com.andy.rios.elektra.ui.util.crearCopiaDeArchivo
import com.andy.rios.elektra.ui.util.showDialogGeneric
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class ContactRegisterFragment : BaseFragment() {
    private lateinit var binding : FragmentContactRegisterBinding
    private val viewModel : ContactRegisterVM by viewModels()
    private var contact: Contact = Contact()
    private val args: ContactRegisterFragmentArgs by navArgs()
    private lateinit var modalBottomSheet : ModalBottomSheet
    private var galleryLauncher: ActivityResultLauncher<Intent>? = null
    private lateinit var cameraExecutor: ExecutorService
    var selectedImageUri : Uri?=null
    private lateinit var requestPermissionLauncher : ActivityResultLauncher<String>
    private var takePicture = false
    private var loadImageOrFileWasCalled = false
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = args.Contact
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                selectedImageUri = result.data?.data
                binding.ivAvatar.setImageURI(selectedImageUri)
            }
        }
        if (!allPermissionsGranted()) {
            requestPermissions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex: Int = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.dismissProgress()
        observeViewModel()
        clickListener()
        validateData()
    }

    private fun validateData(){
        if (contact.id == null){
            binding.btnRegister.text = getString(R.string.register_contact_button)
        }else{
            showData()
            binding.btnRegister.text = getString(R.string.update_contact_button)
        }
    }

    private fun showData(){
        binding.etRegisterContactId.setText(contact.id.toString())
        binding.etRegisterContactName.setText(contact.name.toString())
        binding.etRegisterContactApePat.setText(contact.ape_pat.toString())
        binding.etRegisterContactApeMat.setText(contact.ape_mat.toString())
        binding.etRegisterContactAge.setText(contact.age.toString())
        binding.etRegisterContactPhone.setText(contact.phone.toString())
        if(contact.gender=="F"){
            binding.rbGenderF.isChecked = true
            binding.rbGenderM.isChecked = false
        }else{
            binding.rbGenderM.isChecked = true
            binding.rbGenderF.isChecked = false
        }
        if(!contact.img.isNullOrEmpty()){
            val bitmap: Bitmap = BitmapFactory.decodeFile(contact.img)
            binding.ivAvatar.setImageBitmap(bitmap)
        }else{
            binding.ivAvatar.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_launcher_background))
        }

    }

    private fun generateData() : Contact {
        val gender = if (binding.rbGenderM.isChecked){
            "M"
        }else{
            "F"
        }
        return Contact(
            id = binding.etRegisterContactId.text.toString().toInt(),
            name = binding.etRegisterContactName.text.toString(),
            ape_pat = binding.etRegisterContactApePat.text.toString(),
            ape_mat = binding.etRegisterContactApeMat.text.toString(),
            age = binding.etRegisterContactAge.text.toString().toInt(),
            phone = binding.etRegisterContactPhone.text.toString(),
            gender = gender,
            img = ""
        )
    }

    private fun clickListener(){
        binding.btnRegister.setOnClickListener {
            if (contact.id == null){
                val data = getData()
                viewModel.saveContactLocalId(data)
            }else{
                val editData = generateData()
                viewModel.editContactLocalId(editData)

            }

        }
        binding.ivAvatar.setOnClickListener {
            showModalSheet()
        }
    }

    private fun getData() : Contact{
        val gender = if (binding.rbGenderM.isChecked){
            "M"
        }else{
            "F"
        }
        return Contact(
            name = binding.etRegisterContactName.text.toString(),
            ape_pat = binding.etRegisterContactApePat.text.toString(),
            ape_mat = binding.etRegisterContactApeMat.text.toString(),
            age = binding.etRegisterContactAge.text.toString().toInt(),
            phone = binding.etRegisterContactPhone.text.toString(),
            gender = gender,
            img = selectedImageUri?.let { getRealPathFromUri(requireContext(), it) }
        )
    }

    private fun observeViewModel() = with(viewModel) {
        stateRegisterContact.observe(viewLifecycleOwner){
            when (it) {
                is State.Loading -> {
                    baseActivity.showProgress()
                }
                is State.NoLoading -> {
                    baseActivity.dismissProgress()
                }
                is State.Empty -> {
                    baseActivity.dismissProgress()
                }
                is State.Failed -> {
                    baseActivity.dismissProgress()
                    showDialogFail()
                }
                is State.Success -> {
                    baseActivity.dismissProgress()
                    showDialogRegisterSuccess()
                }

                else -> {}
            }
        }

        stateEditContact.observe(viewLifecycleOwner){
            when (it) {
                is State.Loading -> {
                    baseActivity.showProgress()
                }
                is State.NoLoading -> {
                    baseActivity.dismissProgress()
                }
                is State.Empty -> {
                    baseActivity.dismissProgress()
                }
                is State.Failed -> {
                    baseActivity.dismissProgress()
                    showDialogFail()
                }
                is State.Success -> {
                    baseActivity.dismissProgress()
                    showDialogEditSuccess()
                }

                else -> {}
            }
        }

    }

    private fun showDialogRegisterSuccess(){
        val dialog = DialogModel(
            icono = R.drawable.ic_progress_succes,
            subtitle = getString(R.string.register_contact_message_success),
        )
        baseActivity.showDialogGeneric(dialog)
    }

    private fun showDialogEditSuccess(){
        val dialog = DialogModel(
            icono = R.drawable.ic_progress_succes,
            subtitle = getString(R.string.edit_contact_message_success),
        )
        baseActivity.showDialogGeneric(dialog)
    }

    private fun showDialogFail(){
        val dialog = DialogModel(
            icono = R.drawable.ic_warning,
            subtitle = getString(R.string.register_contact_message_fail),
        )
        baseActivity.showDialogGeneric(dialog)
    }

    private fun showModalSheet(){
        modalBottomSheet = ModalBottomSheet()

        modalBottomSheet.takePicture = {
            cameraExecutor = Executors.newSingleThreadExecutor()
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                launchCamera()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            modalBottomSheet.dismiss()
        }
        modalBottomSheet.uploadFile = {
            openGallery()
            modalBottomSheet.dismiss()
        }

        modalBottomSheet.show(baseActivity.supportFragmentManager, ModalBottomSheet.TAG)
    }

    private fun launchCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePicture = true
        loadImageOrFileWasCalled = true
        resultLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/jpeg,image/png"
        galleryLauncher?.launch(intent)
    }


    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    requireContext(),
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result ->
            if (result.data != null) {
                if (takePicture) {
                    val ba = result.data?.extras?.get("data") as Bitmap
                    val byteStream = ByteArrayOutputStream()
                    ba.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
                    val path: String = MediaStore.Images.Media.insertImage(
                        requireActivity().contentResolver,
                        ba,
                        "FILE-${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg",
                        null
                    )
                    val file = crearCopiaDeArchivo(requireActivity(), Uri.parse(path))
                    ba.recycle()
                }
            }
        }
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                launchCamera()
            }
        }
    }

    fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun checkFileExtension(filePath:Uri) : Boolean {
        val extension = baseActivity.contentResolver.getType(filePath)
        return extension!!.contains("png") || extension.contains("jpg") || extension.contains("jpeg")|| extension.contains("pdf")
    }

    companion object {
        val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    add(Manifest.permission.READ_MEDIA_IMAGES)
                    add(Manifest.permission.READ_MEDIA_VIDEO)
                }else{
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

}