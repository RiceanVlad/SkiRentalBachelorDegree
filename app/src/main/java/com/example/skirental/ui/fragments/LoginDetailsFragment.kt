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
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.example.skirental.viewmodels.LoginDetailsViewModel
import com.example.skirental.R
import com.example.skirental.databinding.LoginDetailsFragmentBinding
import com.example.skirental.models.User
import com.example.skirental.ui.activities.MainActivity
import com.example.skirental.utils.Prefs
import com.example.skirental.utils.bindSignInClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

class LoginDetailsFragment : Fragment(){

    private lateinit var viewModel: LoginDetailsViewModel
    private lateinit var binding: LoginDetailsFragmentBinding
    private lateinit var prefs: Prefs
    private lateinit var user: User
    private lateinit var moshi: Moshi
    private lateinit var jsonAdapter: JsonAdapter<User>
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginDetailsViewModel::class.java]
        binding = LoginDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        prefs = Prefs(requireContext())

        setupSpinnerAdapters()
        seekbarDisplayToast()
        initializeMoshi()
        mAuth = FirebaseAuth.getInstance()
        initializeUser()
        setupObservers()

        return binding.root
    }

    private fun initializeMoshi() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        jsonAdapter = moshi.adapter(User::class.java)
    }

    private fun initializeUser() {
        user = User()
        user.id = mAuth.currentUser?.uid.toString()
        user.name = mAuth.currentUser?.displayName.toString()
        user.email = mAuth.currentUser?.email.toString()
    }

    private fun setupObservers() {
        viewModel.onNextClicked.observe(viewLifecycleOwner, Observer {
            prefs.userDetails = jsonAdapter.toJson(user)
            prefs.userHasDetails = true
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })
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
            binding.spinnerHeight.onItemSelectedListener = object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    Toast.makeText(requireContext(), heightArray[pos], Toast.LENGTH_SHORT).show()
                    user.height = heightArray[pos].toInt()
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
                    Toast.makeText(requireContext(), weightArray[pos], Toast.LENGTH_SHORT).show()
                    user.weight = weightArray[pos].toInt()
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
            val showSizeArray = resources.getStringArray(R.array.shoesize_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerShoesize.adapter = adapter
            binding.spinnerShoesize.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    Toast.makeText(requireContext(), showSizeArray[pos], Toast.LENGTH_SHORT).show()
                    user.shoeSize = showSizeArray[pos].toInt()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }
    }
}