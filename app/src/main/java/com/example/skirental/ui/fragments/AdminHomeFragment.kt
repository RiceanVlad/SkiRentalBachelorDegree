package com.example.skirental.ui.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skirental.R
import com.example.skirental.databinding.AccountFragmentBinding
import com.example.skirental.databinding.FragmentAdminHomeBinding
import com.example.skirental.ui.activities.LoginActivity
import com.example.skirental.viewmodels.AccountViewModel
import com.example.skirental.viewmodels.AdminHomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class AdminHomeFragment : Fragment() {

    private lateinit var viewModel: AdminHomeViewModel
    private lateinit var binding: FragmentAdminHomeBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AdminHomeViewModel::class.java]
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddEquipmentClicked.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminAddEquipmentFragment())
        })
        viewModel.onSignOutClicked.observe(viewLifecycleOwner, Observer {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // it is generated
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            googleSignInClient.signOut()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })
    }

}