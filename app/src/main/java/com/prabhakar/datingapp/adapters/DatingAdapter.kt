package com.prabhakar.datingapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.databinding.ItemUserLayoutBinding
import com.prabhakar.datingapp.model.UserModel
import com.prabhakar.datingapp.view.activity.MessageActivity
import com.prabhakar.datingapp.viewholders.DatingViewHolder

class DatingAdapter(private val userList: ArrayList<UserModel>, val context: Context) :
    RecyclerView.Adapter<DatingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatingViewHolder {
        val view = ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DatingViewHolder(view)
    }

    override fun onBindViewHolder(holder: DatingViewHolder, position: Int) {
        val model = userList[position]
        holder.setData(model)

        holder.binding.btnChat.setOnClickListener {
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("userNumber", model.userNumber)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}