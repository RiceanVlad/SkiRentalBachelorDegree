package com.example.skirental.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.skirental.databinding.DetailsEquipmentFragmentBinding
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.models.User
import com.example.skirental.utils.Prefs
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.DetailsEquipmentViewModelFactory
import com.example.skirental.viewmodelfactories.EquipmentViewModelFactory
import com.example.skirental.viewmodels.DetailsEquipmentViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsEquipmentFragment : Fragment() {

    private lateinit var viewModel: DetailsEquipmentViewModel
    private lateinit var binding: DetailsEquipmentFragmentBinding
    private val args: DetailsEquipmentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = DetailsEquipmentViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailsEquipmentViewModel::class.java]
        binding = DetailsEquipmentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupFlows()
        binding.equipment = args.equipment

        return binding.root
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onAddToCartClicked.collect {
                    addEquipmentToCart(args.equipment)
                }
            }
        }
    }

    private suspend fun addEquipmentToCart(equipment: Equipment) {
        viewModel.addEquipmentToCart(equipment).collect { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    binding.btnAddToCart.isEnabled = false
                }

                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                    binding.btnAddToCart.isEnabled = true
                }

                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
                    binding.btnAddToCart.isEnabled = true
                }
            }
        }
    }
}