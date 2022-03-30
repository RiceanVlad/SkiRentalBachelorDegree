package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.viewmodels.RentChoosePersonViewModel

class RentChoosePersonFragment : Fragment() {

    companion object {
        fun newInstance() = RentChoosePersonFragment()
    }

    private lateinit var viewModel: RentChoosePersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rent_choose_person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RentChoosePersonViewModel::class.java)
        // TODO: Use the ViewModel
    }

}