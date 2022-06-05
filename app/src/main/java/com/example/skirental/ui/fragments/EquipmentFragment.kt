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
import androidx.navigation.fragment.navArgs
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.adapters.EquipmentListener
import com.example.skirental.databinding.EquipmentFragmentBinding
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.ui.activities.MainActivity
import com.example.skirental.utils.Constants
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.EquipmentViewModelFactory
import com.example.skirental.viewmodels.EquipmentViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import com.example.skirental.models.User
import com.example.skirental.utils.Popup
import com.example.skirental.utils.Prefs
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class EquipmentFragment : Fragment() {

    private lateinit var viewModel: EquipmentViewModel
    private lateinit var binding: EquipmentFragmentBinding
    private lateinit var adapter: EquipmentAdapter
    private val storage = Firebase.storage("gs://skirentallicenta-ef1a0.appspot.com")
    private val args: EquipmentFragmentArgs by navArgs()
    private lateinit var prefs: Prefs
    private lateinit var jsonAdapter: JsonAdapter<User>
    private lateinit var user: User
    private lateinit var moshi: Moshi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = EquipmentViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[EquipmentViewModel::class.java]
        binding = EquipmentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        prefs = Prefs(requireContext())

        adapter = EquipmentAdapter(mutableListOf<Equipment>(),EquipmentListener { equipment ->
            findNavController().navigate(EquipmentFragmentDirections.actionEquipmentFragmentToDetailsEquipmentFragment(equipment))
        })
        binding.equipmentList.adapter = adapter
        setupFlows()
        setupTextEquipmentType()
        setupSearch()
        setupObservers()
        initializeMoshi()

//        setupSpinner()

        return  binding.root
    }

    private fun initializeMoshi() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        jsonAdapter = moshi.adapter(User::class.java)
    }

    private fun setupObservers() {
        val popUpClass = Popup()
        viewModel.onShowPopupEvent.observe(viewLifecycleOwner, Observer {
            popUpClass.showPopupWindow(requireView())
        })
        popUpClass.onPersonalFilterState.observe(viewLifecycleOwner, Observer { personalFilter ->
            if(personalFilter) {
                val user = jsonAdapter.fromJson(prefs.userDetails.toString())
                val skiLength = when(user?.experience) {
                    0, 1 -> {
                        val stringBuilder = StringBuilder()
                        for (i in (user.height - Constants.FILTER_SKI_BEGINNER - Constants.FILTER_SKI_LENGTH_MARGIN)..(user.height - Constants.FILTER_SKI_BEGINNER + Constants.FILTER_SKI_LENGTH_MARGIN)) {
                            stringBuilder.append(i)
                            stringBuilder.append(" ")
                        }
                        stringBuilder
                    }
                    2, 3 -> {
                        val stringBuilder = StringBuilder()
                        for (i in (user.height - Constants.FILTER_SKI_INTERMEDIATE - Constants.FILTER_SKI_LENGTH_MARGIN)..(user.height - Constants.FILTER_SKI_INTERMEDIATE + Constants.FILTER_SKI_LENGTH_MARGIN)) {
                            stringBuilder.append(i)
                            stringBuilder.append(" ")
                        }
                        stringBuilder
                    }
                    4, 5 -> {
                        val stringBuilder = StringBuilder()
                        for (i in (user.height - Constants.FILTER_SKI_PRO - Constants.FILTER_SKI_LENGTH_MARGIN)..(user.height - Constants.FILTER_SKI_PRO + Constants.FILTER_SKI_LENGTH_MARGIN)) {
                            stringBuilder.append(i)
                            stringBuilder.append(" ")
                        }
                        stringBuilder
                    }
                    else -> {
                        ""
                    }
                }
                adapter.filter.filter(skiLength)
            } else {
                adapter.filter.filter("")
            }
            println("vlad $personalFilter")
        })
    }

//    private fun setupSpinner() {
//        ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.filter_size,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            val filterArray = resources.getStringArray(R.array.filter_size)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            binding.spinnerFilter.adapter = adapter
//            binding.spinnerFilter.onItemSelectedListener = object :
//                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
////                    val height = filterArray[pos].removeSuffix("cm").toInt()
////                    user.height = height
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                }
//
//                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
//                }
//            }
//        }
//    }

    private fun setupSearch() {
        binding.svSearchEquipment.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setupTextEquipmentType() {
        val title = when(args.equipmentType) {
            EquipmentType.SKI -> {
                Constants.EQUIPMENT_FRAGMENT_LABEL_SKI
            }
            EquipmentType.SKI_BOOTS -> {
                Constants.EQUIPMENT_FRAGMENT_LABEL_SKI_BOOTS
            }
        }
        (activity as MainActivity).supportActionBar?.title = title
        binding.svSearchEquipment.queryHint = "Search ${title.lowercase()}..."
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadEquipments(args.equipmentType)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addEquipmentClickedSharedFlow.collect {
                    addEquipment(Equipment("", EquipmentType.SKI.string,"skis1", 95, 150, 42))
                }
            }
        }
    }

    private suspend fun loadEquipments(equipmentType: EquipmentType) {
        viewModel.getAllEquipments(equipmentType).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    adapter.updateList(state.data as MutableList<Equipment>)
                    adapter.submitList(state.data)
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private suspend fun addEquipment(equipment: Equipment) {
        viewModel.addEquipment(equipment).collect { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
//                    binding.buttonAdd.isEnabled = false
                }

                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
//                    binding.buttonAdd.isEnabled = true
                }

                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
//                    binding.buttonAdd.isEnabled = true
                }
            }
        }
    }
}