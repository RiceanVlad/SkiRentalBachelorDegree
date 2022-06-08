package com.example.skirental.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import com.example.skirental.R
import com.example.skirental.enums.EquipmentType
import com.example.skirental.enums.FilterType

class Popup(equipmentType: EquipmentType?) {

    private val _onApplyFilterEvent = SingleLiveEvent<Pair<FilterType, Int>>()
    val onApplyFilterEvent: LiveData<Pair<FilterType, Int>> = _onApplyFilterEvent

    private val equipmentType = equipmentType
    private var equipmentSize = 0

    //PopupWindow display method
    @SuppressLint("ClickableViewAccessibility")
    fun showPopupWindow(view: View) {
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_filter_window, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        initializeWindowElements(view, popupView, popupWindow)
        setupSpinners(view, popupView)
    }

    private fun initializeWindowElements(view: View, popupView: View, popupWindow: PopupWindow) {
        val closeImageView = popupView.findViewById<ImageView>(R.id.iv_filter_close)
        closeImageView.setOnClickListener {
            popupWindow.dismiss()
        }

        val popupTitle = popupView.findViewById<TextView>(R.id.tv_popup_title)
        popupTitle.text = view.context.getString(R.string.filter_equipments)

        val constraintLayout = popupView.findViewById<ConstraintLayout>(R.id.cl_custom_filter)
        val resetRadioButton = popupView.findViewById<RadioButton>(R.id.rb_filter_reset)
        resetRadioButton.setOnClickListener {
            constraintLayout.visibility = View.GONE
        }

        val personalRadioButton = popupView.findViewById<RadioButton>(R.id.rb_filter_personal)
        personalRadioButton.setOnClickListener {
            constraintLayout.visibility = View.GONE
        }

        val customRadioButton = popupView.findViewById<RadioButton>(R.id.rb_filter_custom)
        customRadioButton.setOnClickListener {
            constraintLayout.visibility = View.VISIBLE
        }

        val radioGroup = popupView.findViewById<RadioGroup>(R.id.rg_filter)
        val buttonApplyFilter = popupView.findViewById<Button>(R.id.btn_apply_filter)
        buttonApplyFilter.setOnClickListener {
            popupWindow.dismiss()
            val selectedId = radioGroup.checkedRadioButtonId
            val radioButton = popupView.findViewById<RadioButton>(selectedId)
            val filterType = getFilterTypeFromRadioButton(radioButton)
            _onApplyFilterEvent.value = Pair(filterType, equipmentSize)
            Toast.makeText(view.context, "Filters applied", Toast.LENGTH_SHORT).show()
        }

        val tvLength = popupView.findViewById<TextView>(R.id.tv_length_label_filter)
        val tvShoeSize = popupView.findViewById<TextView>(R.id.tv_shoesize_label_filter)
        val spinnerLength = popupView.findViewById<Spinner>(R.id.spinner_length_filter)
        val spinnerShoeSize = popupView.findViewById<Spinner>(R.id.spinner_shoesize_filter)

        when(equipmentType) {
            EquipmentType.SKI -> {
                tvShoeSize.visibility = View.GONE
                spinnerShoeSize.visibility = View.GONE
            }
            EquipmentType.SKI_BOOTS -> {
                tvLength.visibility = View.GONE
                spinnerLength.visibility = View.GONE
            }
            else -> {

            }
        }
    }

    private fun getFilterTypeFromRadioButton(view: View?): FilterType {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.rb_filter_reset ->
                    if (checked) {
                        return FilterType.RESET
                    }
                R.id.rb_filter_personal ->
                    if (checked) {
                        return FilterType.PERSONAL_DETAILS
                    }
                R.id.rb_filter_custom ->
                    if (checked) {
                        return FilterType.CUSTOM
                    }
            }
        }
        return FilterType.RESET
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
                    equipmentSize = length
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
                    equipmentSize = shoeSize
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }
    }
}