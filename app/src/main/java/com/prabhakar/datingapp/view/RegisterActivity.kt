package com.prabhakar.datingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Utils.setStatusBarColor(this, R.color.red)
    }
}