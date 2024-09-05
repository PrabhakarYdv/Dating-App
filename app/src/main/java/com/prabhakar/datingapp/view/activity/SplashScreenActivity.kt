package com.prabhakar.datingapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.viewmodel.AuthViewModel

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler(Looper.getMainLooper()).postDelayed({
            if (FirebaseAuth.getInstance().currentUser != null) {
                checkUserExit(FirebaseAuth.getInstance().currentUser?.phoneNumber!!)
                Log.d("auth 35",FirebaseAuth.getInstance().currentUser?.phoneNumber!!)

            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 2000)
    }

    private fun checkUserExit(userNumber: String) {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(userNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        Log.d("auth 51",snapshot.key.toString())
                        startActivity(Intent(this@SplashScreenActivity,HomeActivity::class.java))
                        finish()
                    }
                    else{
                        startActivity(Intent(this@SplashScreenActivity,RegisterActivity::class.java))
                        finish()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("auth",error.message)
                }

            })
    }
}