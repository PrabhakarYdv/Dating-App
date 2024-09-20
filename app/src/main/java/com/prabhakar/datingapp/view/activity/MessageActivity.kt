package com.prabhakar.datingapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.setStatusBarColor(this, R.color.red)

        binding.apply {
            btnSend.setOnClickListener {
                if (yourMessage.text!!.isNotEmpty()) {
                    sendMessage(yourMessage.text.toString())
                }
            }
        }
    }

    private fun sendMessage(message: String) {
        val senderId = Utils.getFirebaseAuthInstance().currentUser?.phoneNumber
        val receiverId = intent.getStringExtra("userNumber")
        val chatId = senderId+receiverId

        val chatMap = hashMapOf<String, String>()
        chatMap["message"] = message
        chatMap["senderId"] = senderId!!

        val reference = FirebaseDatabase.getInstance().getReference("Chats")
            .child(chatId)
        reference.child(reference.push().key!!)
            .setValue(chatMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.yourMessage.text = null
                    binding.yourMessage.requestFocus()
                }
            }

    }
}