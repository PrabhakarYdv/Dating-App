package com.prabhakar.datingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Utils.setStatusBarColor(this, R.color.red)
    }
}