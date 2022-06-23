package com.wk.mediate.present.views.login

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.present.config.Pref
import com.wk.mediate.present.components.toast.ToastView
import com.wk.mediate.databinding.FragmentProfileImageTestBinding
import com.wk.mediate.present.extension.ImageShape
import com.wk.mediate.present.extension.loadUrl
import com.wk.mediate.present.extension.setLocalImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.min


class ProfileImageTestFragment: ViewBindingFragment<FragmentProfileImageTestBinding>() {
    private lateinit var model : ProfileImageViewModel

    private val startForProfileImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == RESULT_OK) {
                    val fileUri = data?.data

                    if (fileUri != null) {
                        binding?.imageProfile?.setLocalImage(fileUri, true)
                    }

                    val filePath = fileUri?.path
                    val stream = ByteArrayOutputStream()
                    resizeBitmap(filePath, 512, 512)?.
                    compress(Bitmap.CompressFormat.JPEG, 90, stream)
                    val file = filePath?.let { File(it) }
                    val requestBody = file?.asRequestBody("image/jpeg".toMediaTypeOrNull())

                    if (requestBody != null) {
                        val fileToUpload = MultipartBody.Part.createFormData("file", file.name, requestBody)
                        model.loadImage(fileToUpload)
                    }


                } else {
                     //Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this).get(ProfileImageViewModel::class.java)
        observeResult()

        Log.d("ProfileUrl", Pref.info?.profile_url.toString())

        binding?.run {
            imageProfile.loadUrl(Pref.info?.profile_url, ImageShape.Circle)

            profileContainer.setOnClickListener {
                showImagePicDialog()
            }
        }
    }

    private fun observeResult() {
        model.getProfileResult().observe(viewLifecycleOwner, {
            Log.d("ProfileUrl", it)

            //TODO 내부 저장소에 저장 안되는 거 수정해야함
            Pref.info?.profile_url = it
            ToastView.show(activity, "프로필 저장완료")
        })
    }

    private fun resizeBitmap(photoPath: String?, targetW: Int, targetH: Int): Bitmap? {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight
        var scaleFactor = 1
        if (targetW > 0 || targetH > 0) {
            scaleFactor = min(photoW / targetW, photoH / targetH)
        }
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        return BitmapFactory.decodeFile(photoPath, bmOptions)
    }

    private fun showImagePicDialog() {
        val options = arrayOf("카메라", "갤러리")
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("프로필 이미지")
        builder.setItems(options) { _, which ->
            if (which == 0) {
                ImagePicker.with(this)
                        .cameraOnly()
                        .cropSquare()
                        .maxResultSize(512, 512)
                        .createIntent { intent ->
                            startForProfileImageResult.launch((intent))
                        }
            } else if (which == 1) {
                ImagePicker.with(this)
                        .galleryOnly()
                        .cropSquare()
                        .maxResultSize(512, 512)
                        .createIntent { intent ->
                            startForProfileImageResult.launch((intent))
                        }
            }
        }
        builder.create().show()
    }
}

//    private fun showImagePicDialog() {
//        val options = arrayOf(getString(R.string.camera), getString(R.string.gallery))
//        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//        builder.setTitle(getString(R.string.profile_image))
//        builder.setItems(options) { _, which ->
//            if (which == 0) {
//                ImagePicker.with(this)
//                        .cameraOnly()
//                        .cropSquare()
//                        .maxResultSize(512, 512)
//                        .createIntent { intent ->
//                            startForProfileImageResult.launch((intent))
//                        }
//            } else if (which == 1) {
//                ImagePicker.with(this)
//                        .galleryOnly()
//                        .cropSquare()
//                        .maxResultSize(512, 512)
//                        .createIntent { intent ->
//                            startForProfileImageResult.launch((intent))
//                        }
//            }
//        }
//        builder.create().show()
//    }
