package com.prabhakar.datingapp.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.databinding.ActivityRegisterBinding
import com.prabhakar.datingapp.model.UserModel
import com.prabhakar.datingapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    private var imageUri: Uri? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        binding.userImage.setImageURI(imageUri)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.setStatusBarColor(this, R.color.red)

        binding.userImage.setOnClickListener {
            selectImage.launch("image/*")
        }
        binding.btnRegister.setOnClickListener {
            saveData()
        }

    }

    private fun saveData() {

        if (binding.userImage == null || imageUri==null) {
            Utils.showToast(this, "Please select a image")
        } else if (binding.userName.text.toString().isEmpty() ||
            binding.userEmail.text.toString().isEmpty() ||
            binding.userCity.text.toString().isEmpty()
        ) {
            Utils.showToast(this, "Please enter all details")
        } else if (!binding.tc.isChecked) {
            Utils.showToast(this, "Please accept the Terms & Condition")
        } else {
            uploadData()
        }
    }

    private fun uploadData() {
        Utils.showDialog(this, "Uploading image ...")
        val userModel = UserModel(
            image = imageUri.toString(),
            userName = binding.userName.text.toString(),
            userEmail = binding.userEmail.text.toString(),
            city = binding.userCity.text.toString()
        )
        authViewModel.uploadImage(imageUri!!, userModel)
        lifecycleScope.launch {
            authViewModel.exposeImageUploadStatus.collect {
                if (it) {
                    Utils.hideDialog()
                    Utils.showToast(applicationContext, "Image Uploaded")
                }
            }
        }



        authViewModel.storeData(imageUri!!, userModel)
        Utils.showDialog(this, "Registering...")
        lifecycleScope.launch {
            authViewModel.exposeDataUploadStatus.collect {
                if (it) {
                    Utils.hideDialog()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                }
            }
        }
    }
}