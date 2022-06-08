package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import com.example.skirental.databinding.CalendarFragmentBinding
import com.example.skirental.utils.Constants
import com.example.skirental.viewmodels.CalendarViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var binding: CalendarFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
        binding = CalendarFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupCalendarApi()

        return binding.root
    }

    private fun setupCalendarApi() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val endDate = today + 2 * Constants.MONTH

        val constraints: CalendarConstraints = CalendarConstraints.Builder()
            .setOpenAt(today)
            .setStart(today)
            .setEnd(endDate)
            .build()

        val materialDatePicker: MaterialDatePicker<Pair<Long, Long>> = MaterialDatePicker
            .Builder
            .dateRangePicker()
            .setCalendarConstraints(constraints)
            .setTitleText("Select a date")
            .build()

        materialDatePicker.show(childFragmentManager, "DATE_RANGE_PICKER")
        materialDatePicker.addOnPositiveButtonClickListener {
            println("vlad: ${it.first} + ${it.second}")
            it.first
            it.second
        }
    }
}