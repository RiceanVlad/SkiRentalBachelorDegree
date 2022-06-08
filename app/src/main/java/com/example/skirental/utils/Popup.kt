package com.example.skirental.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LiveData
import com.example.skirental.R

class Popup {

    private val _onPersonalFilterEvent = SingleLiveEvent<Boolean>()
    val onPersonalFilterEvent : LiveData<Boolean> = _onPersonalFilterEvent

    private val _onCustomFilterLength = SingleLiveEvent<Int>()
    val onCustomFilterLength : LiveData<Int> = _onCustomFilterLength

    private val _onCustomFilterShoeSize = SingleLiveEvent<Int>()
    val onCustomFilterShoeSize : LiveData<Int> = _onCustomFilterShoeSize

    //PopupWindow display method
    @SuppressLint("ClickableViewAccessibility")
    fun showPopupWindow(view: View) {

        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        initializeWindowElements(view, popupView, popupWindow)
        setupSpinners(view, popupView)
    }

    private fun initializeWindowElements(view: View, popupView: View, popupWindow: PopupWindow) {
        val popupTitle = popupView.findViewById<TextView>(R.id.tv_popup_title)
        popupTitle.text = view.context.getString(R.string.filter_equipments)

        val buttonApplyFilter = popupView.findViewById<Button>(R.id.btn_apply_filter)
        buttonApplyFilter.setOnClickListener {
            popupWindow.dismiss()
            Toast.makeText(view.context, "Filters applied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinners(view: View, popupView: View) {
        ArrayAdapter.createFromResource(
            view.context,
            R.array.length_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val lengthArray = view.context.resources.getStringArray(R.array.length_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val spinnerLength = popupView.findViewById<Spinner>(R.id.spinner_length_filter)
            spinnerLength.adapter = adapter
            spinnerLength.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val length = lengthArray[pos].removeSuffix("cm").toInt()
                    _onCustomFilterLength.value = length
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }

        ArrayAdapter.createFromResource(
            view.context,
            R.array.shoesize_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val shoeSizeArray = view.context.resources.getStringArray(R.array.shoesize_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val spinnerShoeSize = popupView.findViewById<Spinner>(R.id.spinner_shoesize_filter)
            spinnerShoeSize.adapter = adapter
            spinnerShoeSize.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val shoeSize = shoeSizeArray[pos].toInt()
                    _onCustomFilterShoeSize.value = shoeSize
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }
    }
}