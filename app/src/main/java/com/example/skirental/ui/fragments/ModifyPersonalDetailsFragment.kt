package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.viewmodels.ModifyPersonalDetailsViewModel

class ModifyPersonalDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ModifyPersonalDetailsFragment()
    }

    private lateinit var viewModel: ModifyPersonalDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modify_personal_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ModifyPersonalDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}