package com.prabhakar.datingapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding =FragmentProfileBinding.inflate(layoutInflater)


        return binding.root
    }


}