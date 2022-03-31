package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.viewmodels.RentDetailsAnotherPersonViewModel

class RentDetailsAnotherPersonFragment : Fragment() {

    companion object {
        fun newInstance() = RentDetailsAnotherPersonFragment()
    }

    private lateinit var viewModel: RentDetailsAnotherPersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rent_details_another_person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RentDetailsAnotherPersonViewModel::class.java)
        // TODO: Use the ViewModel
    }

}