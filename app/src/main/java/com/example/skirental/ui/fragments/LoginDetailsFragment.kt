package com.example.skirental.ui.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.skirental.viewmodels.LoginDetailsViewModel
import com.example.skirental.R
import com.example.skirental.databinding.LoginDetailsFragmentBinding
import com.example.skirental.models.User
import com.example.skirental.ui.activities.MainActivity
import com.example.skirental.utils.Prefs
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.LoginDetailsViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginDetailsFragment : Fragment(){

    private lateinit var viewModel: LoginDetailsViewModel
    private lateinit var binding: LoginDetailsFragmentBinding
    private lateinit var prefs: Prefs
    private lateinit var user: User
    private lateinit var moshi: Moshi
    private lateinit var jsonAdapter: JsonAdapter<User>
    private lateinit var mAuth: FirebaseAuth
    private val args: LoginDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = LoginDetailsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginDetailsViewModel::class.java]
        binding = LoginDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        prefs = Prefs(requireContext())
        mAuth = FirebaseAuth.getInstance()
        initializeMoshi()
        initializeUser()
        setupSpinnerAdapters()
        seekbarDisplayToast()
        setupObservers()
        binding.fromAccountFlow = args.fromAccountFlow

        return binding.root
    }

    private fun initializeMoshi() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        jsonAdapter = moshi.adapter(User::class.java)
    }

    private fun initializeUser() {
        if(args.fromAccountFlow) {
            user = jsonAdapter.fromJson(prefs.userDetails.toString())!!
        } else {
            user = User()
        }
        user.id = mAuth.currentUser?.uid.toString()
        user.name = mAuth.currentUser?.displayName.toString()
        user.email = mAuth.currentUser?.email.toString()
    }

    private fun setupObservers() {
        viewModel.onNextClicked.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                addUserDetailsToFirestore()
            }
            prefs.userDetails = jsonAdapter.toJson(user)
            prefs.userHasDetails = true
            if(args.fromAccountFlow) {
                lifecycleScope.launch {
                    Toast.makeText(requireContext(), "Saving...", Toast.LENGTH_SHORT).show()
                    delay(800)
                    findNavController().popBackStack()
                }
            } else {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        })
    }

    private suspend fun addUserDetailsToFirestore() {
        viewModel.addUserPersonalDetailsToFirestore(user.height, user.weight, user.shoeSize).collect() { state ->
            when(state) {
                is State.Loading -> {

                }
                is State.Success -> {

                }
                is State.Failed -> {

                }
            }
        }
    }

    private fun seekbarDisplayToast() {
            binding.seekbarExperience.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                if (progress < 1) {
                    Toast.makeText(requireContext(), "Beginner", Toast.LENGTH_SHORT).show()
                    user.experience = 0
                }
                if (progress == 2 || progress == 3) {
                    Toast.makeText(requireContext(), "Intermediate", Toast.LENGTH_SHORT).show()
                    user.experience = 2
                }
                if (progress > 3 && progress < 5) {
                    Toast.makeText(requireContext(), "Advanced", Toast.LENGTH_SHORT).show()
                    user.experience = 4
                }
                if (progress == 5) {
                    Toast.makeText(requireContext(), "Pro", Toast.LENGTH_SHORT).show()
                    user.experience = 5
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setupSpinnerAdapters() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.height_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val heightArray = resources.getStringArray(R.array.height_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerHeight.adapter = adapter
            val userHeight: String = user.height.toString() + "cm"
            val position = adapter.getPosition(userHeight)
            binding.spinnerHeight.setSelection(position)
            binding.spinnerHeight.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val height = heightArray[pos].removeSuffix("cm").toInt()
                    user.height = height
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.weight_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val weightArray = resources.getStringArray(R.array.weight_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerWeight.adapter = adapter
            binding.spinnerWeight.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val weight = weightArray[pos].removeSuffix("kg").toInt()
                    user.weight = weight
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.shoesize_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val shoeSizeArray = resources.getStringArray(R.array.shoesize_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerShoesize.adapter = adapter
            binding.spinnerShoesize.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val shoeSize = shoeSizeArray[pos].toInt()
                    user.shoeSize = shoeSize
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }
    }
}