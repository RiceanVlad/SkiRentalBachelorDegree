package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.adapters.EquipmentListener
import com.example.skirental.databinding.CartFragmentBinding
import com.example.skirental.models.Equipment
import com.example.skirental.utils.Prefs
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.CartViewModelFactory
import com.example.skirental.viewmodelfactories.EquipmentViewModelFactory
import com.example.skirental.viewmodels.CartViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: CartFragmentBinding
    private lateinit var adapter: EquipmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = CartViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
        binding = CartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = EquipmentAdapter(EquipmentListener { equipment -> })
        binding.rvCartEquipmentList.adapter = adapter
        setupFlows()

        return  binding.root
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadEquipmentsToCart()
        }
    }

    private suspend fun loadEquipmentsToCart() {
        viewModel.getAllCartEquipments().collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    adapter.submitList(state.data)
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
