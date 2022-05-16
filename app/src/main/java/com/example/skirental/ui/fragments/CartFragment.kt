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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.adapters.EquipmentListener
import com.example.skirental.databinding.CartFragmentBinding
import com.example.skirental.models.Equipment
import com.example.skirental.models.User
import com.example.skirental.utils.Prefs
import com.example.skirental.utils.collectLatestLifecycleFlow
import com.example.skirental.viewmodels.CartViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: CartFragmentBinding
    private lateinit var adapter: EquipmentAdapter
    private lateinit var prefs: Prefs
    private lateinit var moshi: Moshi
    private lateinit var jsonAdapter: JsonAdapter<MutableList<Equipment?>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        binding = CartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        prefs = Prefs(requireContext())
        initializeMoshi()

        adapter = EquipmentAdapter(EquipmentListener { equipment ->
        })
        binding.rvCartEquipmentList.adapter = adapter

        val equipments = jsonAdapter.fromJson(prefs.cartItems.toString())

        adapter.submitList(equipments)

        return  binding.root
    }

    private fun initializeMoshi() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(MutableList::class.java, Equipment::class.java)
        jsonAdapter = moshi.adapter(type)
    }

}
