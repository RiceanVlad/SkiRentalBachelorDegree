package com.example.skirental.ui.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.databinding.AccountFragmentBinding
import com.example.skirental.databinding.FragmentAccountDetailsBinding
import com.example.skirental.viewmodels.AccountDetailsViewModel
import com.example.skirental.viewmodels.AccountViewModel
import com.google.firebase.auth.FirebaseAuth

class AccountDetailsFragment : Fragment() {

    private lateinit var viewModel: AccountDetailsViewModel
    private lateinit var binding: FragmentAccountDetailsBinding
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AccountDetailsViewModel::class.java]
        binding = FragmentAccountDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        mAuth = FirebaseAuth.getInstance()
        binding.tvAccountDetailsEmail.text = "Email:   ${mAuth.currentUser?.email}"
        binding.tvAccountDetailsName.text = "Name:  ${mAuth.currentUser?.displayName}"

        return binding.root
    }

}