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
import com.example.skirental.viewmodels.DetailsEquipmentViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch

class DetailsEquipmentFragment : Fragment() {

    private lateinit var viewModel: DetailsEquipmentViewModel
    private lateinit var binding: DetailsEquipmentFragmentBinding
    private val args: DetailsEquipmentFragmentArgs by navArgs()
    private lateinit var prefs: Prefs
    private lateinit var moshi: Moshi
    private lateinit var jsonAdapter: JsonAdapter<MutableList<Equipment>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DetailsEquipmentViewModel::class.java]
        binding = DetailsEquipmentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        prefs = Prefs(requireContext())
        initializeMoshi()
        setupFlows()

        return binding.root
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onAddToCartClicked.collect {
                    val equipments = jsonAdapter.fromJson(prefs.cartItems ?: "")
                    equipments?.add(args.equipment)
                    prefs.cartItems = jsonAdapter.toJson(equipments)
                }
            }
        }
    }

    private fun initializeMoshi() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(MutableList::class.java, Equipment::class.java)
        jsonAdapter = moshi.adapter(type)
    }
}