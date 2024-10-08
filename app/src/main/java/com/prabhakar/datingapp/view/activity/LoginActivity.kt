package com.prabhakar.datingapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.databinding.ActivityLoginBinding
import com.prabhakar.datingapp.model.UserModel
import com.prabhakar.datingapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.setStatusBarColor(this, R.color.red)

        binding.btnSendOTP.setOnClickListener {
            if (binding.userNumber.text!!.isEmpty() || binding.userNumber.text!!.length < 10) {
                binding.userNumber.error = "Please enter valid number"
            } else {
                binding.otpLayout.visibility = View.VISIBLE
                binding.btnSendOTP.visibility = View.GONE
                binding.bntVerifyOTP.visibility = View.VISIBLE
                binding.changeNumber.visibility = View.VISIBLE

                sendOTP(binding.userNumber.text.toString())
            }
        }

        binding.changeNumber.setOnClickListener {
            binding.otpLayout.visibility = View.GONE
            binding.btnSendOTP.visibility = View.VISIBLE
            binding.bntVerifyOTP.visibility = View.GONE
            binding.changeNumber.visibility = View.GONE

            binding.userNumber.requestFocus()
        }

        binding.bntVerifyOTP.setOnClickListener {
            verifyOTP(binding.userNumber.text.toString(), binding.otp.text.toString())
        }
    }

    private fun sendOTP(userNumber: String) {
        Utils.showDialog(this, "Sending OTP...")
        authViewModel.sendOTP(userNumber, this)
        lifecycleScope.launch {
            authViewModel.exposeOtp.collect {
                if (it) {
                    Utils.hideDialog()
                    Utils.showToast(this@LoginActivity, "OTP has been sent")
                } else {
//                    Utils.showToast(this@LoginActivity, "Something went wrong")
                }
            }
        }
    }


    private fun verifyOTP(userNumber: String, otp: String) {
        Utils.showDialog(this, "Verifying...")

        val userModel = Utils.getUId()?.let {
            UserModel(it, userNumber)
        }

        authViewModel.signInWithPhoneAuth(userNumber, otp, userModel)

        lifecycleScope.launch {
            authViewModel.exposeVerifyStatus.collect {
                if (it) {
                    Utils.hideDialog()
                    checkUserExit(userNumber)
//                    authViewModel.exposeUserRegisterStatus.collect { isUserRegister ->
//                        if (isUserRegister) {
//                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
//                        } else {
//                            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
//                            finish()
//                        }
//                    }
                } else {
//                    Utils.showToast(this@LoginActivity, "Enter a valid OTP")
//                    binding.otp.error= "Incorrect OTP"
                }
            }
        }
    }

    private fun checkUserExit(userNumber: String?) {
        FirebaseDatabase.getInstance().getReference("Users")
            .child("+91$userNumber")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Utils.hideDialog()
                    if (snapshot.exists()) {
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Utils.hideDialog()
                    Utils.showToast(this@LoginActivity, "${error.message}")
                }

            })
    }
}