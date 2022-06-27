package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skirental.databinding.SearchFragmentBinding
import com.example.skirental.enums.EquipmentType
import com.example.skirental.viewmodels.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupObservers()
        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.svSerachScroll.viewTreeObserver.addOnScrollChangedListener {
            binding.ivSearchArrowDown.visibility = View.GONE
        }
    }

    private fun setupObservers() {
        viewModel.onNavigateToEquipmentFragment.observe(viewLifecycleOwner, Observer { equipmentType ->
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToEquipmentFragment(equipmentType))
        })
    }
}