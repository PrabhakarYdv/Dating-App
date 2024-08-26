package com.prabhakar.datingapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

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
            }
        }

        binding.changeNumber.setOnClickListener {
            binding.otpLayout.visibility = View.GONE
            binding.btnSendOTP.visibility = View.VISIBLE
            binding.bntVerifyOTP.visibility = View.GONE
            binding.changeNumber.visibility = View.GONE

            binding.userNumber.requestFocus()
        }
    }
}