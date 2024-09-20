package com.prabhakar.datingapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.prabhakar.datingapp.Utils
import com.prabhakar.datingapp.adapters.DatingAdapter
import com.prabhakar.datingapp.databinding.FragmentDatingBinding
import com.prabhakar.datingapp.model.UserModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class DatingFragment : Fragment() {
    private lateinit var binding: FragmentDatingBinding
    private lateinit var cardStackLayoutManager: CardStackLayoutManager
    companion object{
        var usersList:ArrayList<UserModel>?=null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatingBinding.inflate(layoutInflater)

        getData()

        binding
        return binding.root
    }

    private fun init() {
        cardStackLayoutManager = CardStackLayoutManager(requireContext(),
            object : CardStackListener {
                override fun onCardDragging(direction: Direction?, ratio: Float) {

                }

                override fun onCardSwiped(direction: Direction?) {
                    if (cardStackLayoutManager?.topPosition == usersList?.size) {
                        Utils.showToast(requireContext(), "This is the last card")
                    }
                }

                override fun onCardRewound() {
                }

                override fun onCardCanceled() {
                }

                override fun onCardAppeared(view: View?, position: Int) {
                }

                override fun onCardDisappeared(view: View?, position: Int) {
                }

            })
        cardStackLayoutManager.setVisibleCount(3)
        cardStackLayoutManager.setTranslationInterval(0.5f)
        cardStackLayoutManager.setScaleInterval(0.8f)
        cardStackLayoutManager.setMaxDegree(20.0f)
        cardStackLayoutManager.setDirections(Direction.HORIZONTAL)
    }

    private fun getData() {
        FirebaseDatabase.getInstance().getReference("Users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        usersList=arrayListOf<UserModel>()
                        for (data in snapshot.children) {
                            val model = data.getValue(UserModel::class.java)
                            if (model?.uid != FirebaseAuth.getInstance().currentUser?.uid && model != null) {
                                usersList?.add(model)
                            }

                        }
                        usersList?.shuffle()
                        init()
                        binding.cardStackView.layoutManager = cardStackLayoutManager
                        binding.cardStackView.itemAnimator = DefaultItemAnimator()
                        binding.cardStackView.adapter = DatingAdapter(usersList!!,requireContext())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Utils.showToast(requireContext(), "${error.message}")
                }

            })
    }

}