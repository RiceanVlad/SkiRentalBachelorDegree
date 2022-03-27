package com.example.skirental.ui.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.skirental.R
import com.example.skirental.databinding.StartFragmentBinding
import com.example.skirental.ui.activities.MainActivity
import com.example.skirental.utils.Prefs
import com.example.skirental.viewmodels.StartViewModel
import com.google.firebase.auth.FirebaseAuth

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: StartFragmentBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[StartViewModel::class.java]
        binding = StartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        prefs = Prefs(requireContext())

        if (user == null) {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToSignInFragment())
        } else {
            if(!prefs.userHasDetails) {
                findNavController().navigate(StartFragmentDirections.actionStartFragmentToLoginDetailsFragment())
            } else {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        return binding.root
    }
}