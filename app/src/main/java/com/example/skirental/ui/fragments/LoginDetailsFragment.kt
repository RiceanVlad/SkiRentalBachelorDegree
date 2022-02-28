package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.viewmodels.LoginDetailsViewModel
import com.example.skirental.R

class LoginDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = LoginDetailsFragment()
    }

    private lateinit var viewModel: LoginDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}