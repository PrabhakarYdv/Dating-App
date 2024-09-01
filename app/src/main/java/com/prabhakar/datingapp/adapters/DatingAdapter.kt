package com.prabhakar.datingapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.datingapp.databinding.ItemUserLayoutBinding
import com.prabhakar.datingapp.model.UserModel
import com.prabhakar.datingapp.viewholders.DatingViewHolder

class DatingAdapter(private val userList: ArrayList<UserModel>) :
    RecyclerView.Adapter<DatingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatingViewHolder {
        val view = ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: DatingViewHolder, position: Int) {
        val model = userList[position]
        holder.setData(model)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}