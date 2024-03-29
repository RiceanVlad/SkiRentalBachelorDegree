package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.adapters.EquipmentListener
import com.example.skirental.databinding.CartFragmentBinding
import com.example.skirental.models.Equipment
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.CartViewModelFactory
import com.example.skirental.viewmodels.CartViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: CartFragmentBinding
    private lateinit var adapter: EquipmentAdapter
    private var totalPrice = 0
    private lateinit var equipmentList: Array<Equipment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = CartViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
        binding = CartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = EquipmentAdapter(mutableListOf<Equipment>(), EquipmentListener { equipment ->
            createRemoveEquipmentAlert(equipment)
        })
        binding.rvCartEquipmentList.adapter = adapter
        setupFlows()
        setupObservers()
        lifecycleScope.launch {
            delay(1200)
            setupListeners()
        }

        return  binding.root
    }

    private fun setupListeners() {
        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            binding.ivSearchArrowDown.visibility = View.GONE
        }    }

    private fun setupObservers() {
        viewModel.onNavigateToSearchScreen.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToSearchFragment())
        })
        viewModel.onNavigateToCalendarScreen.observe(viewLifecycleOwner, Observer {
            if(totalPrice > 0) {
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToCalendarFragment(equipmentList, totalPrice, binding.etAddComment.text.toString()))
            } else {
                Toast.makeText(requireContext(), "Please add items first", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadEquipmentsToCart()
        }
    }

    private fun createRemoveEquipmentAlert(equipment: Equipment): AlertDialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Remove item")
        builder.setMessage("Are you sure you want to remove this item from cart?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {
                dialog, _ -> dialog.cancel()
            lifecycleScope.launch {
                onRemoveEquipmentFromCart(equipment)
            }
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        return builder.show()
    }

    private suspend fun onRemoveEquipmentFromCart(equipment: Equipment) {
        viewModel.removeEquipmentFromCart(equipment).collect() { state ->
            when(state) {
                is State.Loading -> {
                    binding.isLoading = true
                }
                is State.Success -> {
                    loadEquipmentsToCart()
                    binding.isLoading = false

                }
                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    binding.isLoading = false

                }
            }
        }
    }

    private suspend fun loadEquipmentsToCart() {
        viewModel.getAllCartEquipments().collect() { state ->
            when(state) {
                is State.Loading -> {
                    binding.isLoading = true
                }
                is State.Success -> {
                    equipmentList = state.data.toTypedArray()
                    adapter.submitList(state.data)
                    if(adapter.itemCount == 0) {
                        binding.ivSearchArrowDown.visibility = View.GONE
                    }
                    state.data.forEach {
                        totalPrice += it.price
                    }
                    binding.totalPrice = totalPrice
                    binding.isLoading = false
                }
                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
                    binding.isLoading = false
                }
            }
        }
    }
}
