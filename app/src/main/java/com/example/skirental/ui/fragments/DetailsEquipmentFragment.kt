package com.example.skirental.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.skirental.databinding.DetailsEquipmentFragmentBinding
import com.example.skirental.models.Equipment
import com.example.skirental.models.User
import com.example.skirental.utils.Prefs
import com.example.skirental.viewmodels.DetailsEquipmentViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DetailsEquipmentFragment : Fragment() {

    private lateinit var viewModel: DetailsEquipmentViewModel
    private lateinit var binding: DetailsEquipmentFragmentBinding
    private val args: DetailsEquipmentFragmentArgs by navArgs()
    private lateinit var prefs: Prefs
    private lateinit var moshi: Moshi
    private lateinit var jsonAdapter: JsonAdapter<Equipment>

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

//        Toast.makeText(requireContext(), "${args.equipment.id} + ${args.equipment.type}", Toast.LENGTH_SHORT).show()
        prefs.cartItems = jsonAdapter.toJson(args.equipment)

        return binding.root
    }

    private fun initializeMoshi() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        jsonAdapter = moshi.adapter(Equipment::class.java)
    }
}