package com.prabhakar.datingapp.viewmodel

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {
    private val _verificationId = MutableStateFlow<String?>(null)
    private val _otpSent = MutableStateFlow(false)
    val exposeOtp = _otpSent
    private val _isVerifySuccess = MutableStateFlow(false)
    val exposeVerifyStatus = _isVerifySuccess
    private val _isCurrentUser = MutableStateFlow(false)
    val exposeCurrentUserStatus = _isCurrentUser
    private val _isImageUpload = MutableStateFlow(false)
    val exposeImageUploadStatus = _isImageUpload
    private val _dataUpload = MutableStateFlow(false)
    val exposeDataUploadStatus = _dataUpload
    private val _isUserRegister = MutableStateFlow(false)
    val exposeUserRegisterStatus = _isUserRegister

    init {
        Utils.getFirebaseAuthInstance().currentUser?.let {
            _isCurrentUser.value = true
        }
    }

    fun sendOTP(userNumber: String, activity: Activity) {
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(
                verficationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                _verificationId.value = verficationId
                _otpSent.value = true
            }
        }

        val option = PhoneAuthOptions.newBuilder(Utils.getFirebaseAuthInstance())
            .setPhoneNumber("+91${userNumber}")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    fun signInWithPhoneAuth(userNumber: String, otp: String, userModel: UserModel?) {
        val credential = PhoneAuthProvider.getCredential(_verificationId.value.toString(), otp)

        Utils.getFirebaseAuthInstance().signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child("User").child(userModel?.uid.toString())
                        .setValue(userModel)
                    _isVerifySuccess.value = true
                } else {
                    _isVerifySuccess.value = false
                }
            }
    }

    fun uploadImage(imageUri: Uri, userModel: UserModel?) {
        val storageRef = FirebaseStorage.getInstance().getReference("Profile")
            .child(Utils.getUId().toString())
            .child(Utils.getFirebaseAuthInstance().currentUser?.phoneNumber!!)
            .child("Profile.jpg")
        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    storeData(it,userModel)
                    Log.d("URL",it.toString())

                    _isImageUpload.value = true
//                    storeData(imageUri, userModel)
                }
            }
    }

    fun storeData(imageUri: Uri, userModel: UserModel?) {
        userModel?.image = imageUri.toString()
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser?.phoneNumber!!)
            .setValue(userModel)
            .addOnCompleteListener {
                _dataUpload.value = true
                _isUserRegister.value = true
            }.addOnSuccessListener {
                _dataUpload.value = true
                _isUserRegister.value = true
            }
            .addOnFailureListener {
                _dataUpload.value = false
                _isUserRegister.value = false
            }
    }
}