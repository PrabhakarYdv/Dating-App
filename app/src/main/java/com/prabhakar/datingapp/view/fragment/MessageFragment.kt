package com.prabhakar.datingapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.adapters.ChatAdapter
import com.prabhakar.datingapp.databinding.FragmentMessageBinding


class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private lateinit var adapter: ChatAdapter
    private val chatList = arrayListOf<String>()
    private val chatKeyList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(layoutInflater)
        getData()
        return binding.root


    }

    private fun getData() {
        val currentId = Utils.getFirebaseAuthInstance().currentUser?.phoneNumber

        FirebaseDatabase.getInstance().getReference("Chats")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        if (data.exists()) {
                            if (data?.key!!.contains(currentId!!)) {
                                chatList.add(data.key!!.replace(currentId, ""))
                                chatKeyList.add(data.key!!)
                            }
                        }
                    }
                    setRecyclerView()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun setRecyclerView() {
        adapter = ChatAdapter(chatList,chatKeyList)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRecyclerView.adapter = adapter
    }
}