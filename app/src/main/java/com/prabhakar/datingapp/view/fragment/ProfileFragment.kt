package com.prabhakar.datingapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.prabhakar.datingapp.R
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.databinding.FragmentProfileBinding
import com.prabhakar.datingapp.model.UserModel
import com.prabhakar.datingapp.view.activity.LoginActivity

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        Utils.showDialog(requireContext(), "Loading Profile")
        setData()

        binding.btnLogout.setOnClickListener {
            Utils.showDialog(requireContext(),"Logging Out...")
            Utils.getFirebaseAuthInstance().signOut()
            Utils.hideDialog()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return binding.root


    }

    private fun setData() {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser?.phoneNumber!!).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.getValue(UserModel::class.java)
                    binding.apply {
                        userName.setText(data?.userName)
                        userEmail.setText(data?.userEmail)
                        userNumber.setText(FirebaseAuth.getInstance().currentUser?.phoneNumber!!)
                        userCity.setText(data?.city)

                        Glide.with(userImage).load(data?.image).placeholder(R.drawable.avatar)
                            .into(userImage)
                        Utils.hideDialog()
                    }
                }
            }
    }
}