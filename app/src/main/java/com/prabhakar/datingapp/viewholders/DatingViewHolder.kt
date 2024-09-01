package com.prabhakar.datingapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prabhakar.datingapp.databinding.ItemUserLayoutBinding
import com.prabhakar.datingapp.model.UserModel

class DatingViewHolder(val binding: ItemUserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(model: UserModel) {
        binding.apply {
            name.text = model.userName
            email.text = model.userEmail

            Glide.with(userImage).load(model.image).into(userImage)
        }
    }
}