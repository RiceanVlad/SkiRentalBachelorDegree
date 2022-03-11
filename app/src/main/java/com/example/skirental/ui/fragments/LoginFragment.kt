package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skirental.viewmodels.LoginViewModel
import com.example.skirental.R
import com.example.skirental.databinding.LoginFragmentBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.btnNavigateToDetails.setOnClickListener {
            updateFirestoreScore(10)
            val action = LoginFragmentDirections.actionLoginFragmentToLoginDetailsFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateFirestoreScore(score: Int) {
        db.collection("users").document("RPdAQ3XEOb9iK66uxqZz")
            .update("score", score)
    }

}