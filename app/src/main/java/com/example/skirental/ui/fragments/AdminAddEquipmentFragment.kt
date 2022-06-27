package com.example.skirental.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.skirental.R
import com.example.skirental.databinding.FragmentAdminAddEquipmentBinding
import com.example.skirental.databinding.StartFragmentBinding
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.AdminAddEquipmentViewModelFactory
import com.example.skirental.viewmodels.AdminAddEquipmentViewModel
import com.example.skirental.viewmodels.StartViewModel
import kotlinx.coroutines.launch
import java.util.*

class AdminAddEquipmentFragment : Fragment() {

    private lateinit var viewModel: AdminAddEquipmentViewModel
    private lateinit var binding: FragmentAdminAddEquipmentBinding
    private var equipment = Equipment(type = EquipmentType.SKI.string)
    private var isImageReady = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = AdminAddEquipmentViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AdminAddEquipmentViewModel::class.java]
        binding = FragmentAdminAddEquipmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupSpinnerAdapters()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddImageClicked.observe(viewLifecycleOwner, Observer {
            openGalleryForImage()
        })
    }

    private fun setupSpinnerAdapters() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.equipment_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val equipmentTypeArray = resources.getStringArray(R.array.equipment_type_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAddEquipmentType.adapter = adapter
            binding.spinnerAddEquipmentType.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val equipmentType = equipmentTypeArray[pos]
                    binding.equipmentType = EquipmentType.valueOf(equipmentType)
                    equipment.type = equipmentType
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        resultLauncherImage.launch(intent)
    }

    private val resultLauncherImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data?.data != null) {
                val fileUri: Uri = data.data!!
                uploadImageToFirebase(fileUri)
                // add to imageView
                Glide.with(requireView())
                    .load(fileUri)
                    .listener( object : RequestListener<Drawable> {
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            isImageReady = true
                            return false
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    })
                    .into(binding.ivAddEquipmentImage)
            }
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        lifecycleScope.launch {
            addImageToStorage(fileUri)
        }
    }

    private suspend fun addImageToStorage(fileUri: Uri) {
        viewModel.addEquipmentImageToStorage(equipment, fileUri).collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }
}