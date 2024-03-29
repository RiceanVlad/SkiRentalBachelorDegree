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
        })
    }

    private suspend fun addUserDetailsToFirestore() {
        viewModel.addUserPersonalDetailsToFirestore(user.height, user.weight, user.shoeSize, user.experience).collect() { state ->
            when(state) {
                is State.Loading -> {
                    binding.isLoading = true
                }
                is State.Success -> {
                    binding.isLoading = false
                    if(args.fromAccountFlow) {
                        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
                is State.Failed -> {
                    binding.isLoading = false

                    Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun seekbarDisplayToast() {
            binding.seekbarExperience.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                if (progress < 1) {
                    user.experience = 0
                }
                if (progress == 2 || progress == 3) {
                    user.experience = 2
                }
                if (progress > 3 && progress < 5) {
                    user.experience = 4
                }
                if (progress == 5) {
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
            if(args.fromAccountFlow) {
                val userHeight: String = user.height.toString() + "cm"
                val position = adapter.getPosition(userHeight)
                binding.spinnerHeight.setSelection(position)
            }
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
            if(args.fromAccountFlow) {
                val userWeight: String = user.weight.toString() + "kg"
                val position = adapter.getPosition(userWeight)
                binding.spinnerWeight.setSelection(position)
            }
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
            if(args.fromAccountFlow) {
                val userShoeSize: String = user.shoeSize.toString()
                val position = adapter.getPosition(userShoeSize)
                binding.spinnerShoesize.setSelection(position)
            }
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