package com.prabhakar.datingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.databinding.ChatLayoutBinding
import com.prabhakar.datingapp.model.UserModel
import com.prabhakar.datingapp.viewholders.ChatViewHolder

class ChatAdapter(private val chatList: ArrayList<String>, val chatKey: ArrayList<String>) :
    RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val model = chatList[position]
//        holder.setData(model)

        FirebaseDatabase.getInstance().getReference("Users")
            .child(model)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.getValue(UserModel::class.java)
                        holder.binding.apply {
                            userName.text = data?.userName

                            Glide.with(userImage).load(data?.image).placeholder(R.drawable.avatar).into(userImage)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


    }

    override fun getItemCount(): Int {
        return chatList.size
    }

}