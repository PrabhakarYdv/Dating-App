package com.prabhakar.datingapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prabhakar.datingapp.adapters.ChatAdapter
import com.prabhakar.datingapp.databinding.FragmentMessageBinding


class MessageFragment : Fragment() {
    private lateinit var binding: FragmentMessageBinding
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(layoutInflater)

        setRecyclerView()
        return binding.root


    }

    private fun setRecyclerView() {
        adapter = ChatAdapter(DatingFragment.usersList!!)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRecyclerView.adapter = adapter
    }
}