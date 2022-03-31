package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.viewmodels.RentChooseTypeOfRentViewModel

class RentChooseTypeOfRentFragment : Fragment() {

    companion object {
        fun newInstance() = RentChooseTypeOfRentFragment()
    }

    private lateinit var viewModel: RentChooseTypeOfRentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rent_choose_type_of_rent_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RentChooseTypeOfRentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}