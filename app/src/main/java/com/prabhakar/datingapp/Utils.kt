package com.prabhakar.datingapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.prabhakar.datingapp.databinding.ProgressBarBinding

object Utils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private var dialog: AlertDialog? = null
    fun showDialog(context: Context, content: String) {
        val progressBar = ProgressBarBinding.inflate(LayoutInflater.from(context))
        progressBar.message.text = content
        dialog = AlertDialog.Builder(context)
            .setView(progressBar.root)
            .setCancelable(false)
            .create()
        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }

    private var firebaseAuthInstance: FirebaseAuth? = null

    fun getFirebaseAuthInstance(): FirebaseAuth {
        if (firebaseAuthInstance == null) {
            firebaseAuthInstance = FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }

    fun getUId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun setStatusBarColor(activity: Activity, color: Int) {
        activity?.window?.apply {
            val color = ContextCompat.getColor(activity, color)
            statusBarColor = color
        }
    }
}