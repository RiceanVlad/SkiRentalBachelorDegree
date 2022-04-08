package com.example.skirental.ui.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.skirental.viewmodels.LoginDetailsViewModel
import com.example.skirental.R
import com.example.skirental.databinding.LoginDetailsFragmentBinding
import com.example.skirental.models.User
import com.example.skirental.ui.activities.MainActivity
import com.example.skirental.utils.Prefs
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

class LoginDetailsFragment : Fragment() {

    private lateinit var viewModel: LoginDetailsViewModel
    private lateinit var binding: LoginDetailsFragmentBinding
    private lateinit var prefs: Prefs

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
        setupObservers()

        /**
         * Moshi
         */
        val user = User(
            "qweasd",
            "vlad"
        )
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
//        jsonAdapter.toJson(user)
        println(jsonAdapter.toJson(user))

        val asd = 1

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onNextClicked.observe(viewLifecycleOwner, Observer {
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
//                dificultate = progress
                if (progress < 1)
                    Toast.makeText(requireContext(), "Beginner", Toast.LENGTH_SHORT).show()
                if (progress == 2 || progress == 3)
                    Toast.makeText(requireContext(), "Intermediate", Toast.LENGTH_SHORT).show()
                if (progress > 3 && progress < 5)
                    Toast.makeText(requireContext(), "Advanced", Toast.LENGTH_SHORT).show()
                if (progress == 5)
                    Toast.makeText(requireContext(), "Pro", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinnerAdapters() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.height_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerHeight.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.weight_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerWeight.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.shoesize_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerShoesize.adapter = adapter
        }
    }
}