package com.prabhakar.datingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.datingapp.databinding.ChatLayoutBinding
import com.prabhakar.datingapp.model.UserModel
import com.prabhakar.datingapp.viewholders.ChatViewHolder

class ChatAdapter(private val chatList: ArrayList<UserModel>) :
    RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val model = chatList[position]
        holder.setData(model)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

}