package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.skirental.databinding.CartFragmentBinding
import com.example.skirental.utils.collectLatestLifecycleFlow
import com.example.skirental.viewmodels.CartViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: CartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        binding = CartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        collectLatestLifecycleFlow(viewModel.stateFlow) { number ->
//            binding.tvState.text = number.toString()
//        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collectLatest { number ->
                    binding.tvState.text = number.toString()
                }
            }
        }

        collectLatestLifecycleFlow(viewModel.sharedFlow) {
//            Toast.makeText(requireContext(), "LUL", Toast.LENGTH_SHORT).show()
        }

        val storage = FirebaseStorage.getInstance()
        val gsReference = storage.getReferenceFromUrl("gs://skirentallicenta-ef1a0.appspot.com/Skis/BfPU3SOxko59exhGHroa.jpeg")

        Glide.with(this).load(gsReference).into(binding.ivTest)





        return  binding.root
    }
}
