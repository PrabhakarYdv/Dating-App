package com.prabhakar.datingapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.databinding.ChatLayoutBinding
import com.prabhakar.datingapp.model.UserModel

class ChatViewHolder( val binding: ChatLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(model: String) {
        binding.apply {
//            userName.text = model.userName
//
//            Glide.with(userImage).load(model.image).placeholder(R.drawable.avatar).into(userImage)
        }
    }
}