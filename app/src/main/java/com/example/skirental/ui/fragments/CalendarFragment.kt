package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.skirental.databinding.CalendarFragmentBinding
import com.example.skirental.utils.Constants
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.CalendarViewModelFactory
import com.example.skirental.viewmodelfactories.CartViewModelFactory
import com.example.skirental.viewmodels.CalendarViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var binding: CalendarFragmentBinding
    private val args: CalendarFragmentArgs by navArgs()
    private var navigateToPayment = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = CalendarViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[CalendarViewModel::class.java]
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
            .setValidator(DateValidatorPointForward.from(today))
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
            navigateToPayment = true
            lifecycleScope.launch {
                addRentDates(getStringDate(it.first), getStringDate(it.second))
            }
            findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToPayFragment(args.equipmentList, args.price, args.additionalComment))
        }
        materialDatePicker.addOnDismissListener {
            if(!navigateToPayment) {
                findNavController().popBackStack()
            }
        }
    }

    private fun getStringDate(milliseconds: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale.ENGLISH)
        return simpleDateFormat.format(milliseconds)
    }

    private suspend fun addRentDates(dateStart: String, dateEnd: String) {
        viewModel.addRentDates(dateStart, dateEnd).collect() { state ->
            when(state) {
                is State.Loading -> {
//                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
//                    Toast.makeText(requireContext(), "Added dates", Toast.LENGTH_SHORT).show()
                }
                is State.Failed -> {
//                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT)
//                        .show()
                }
            }
        }
    }
}