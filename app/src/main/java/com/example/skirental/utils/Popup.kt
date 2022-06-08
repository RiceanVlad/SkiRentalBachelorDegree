package com.example.skirental.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import com.example.skirental.R

class Popup {

    private val _onPersonalFilterState = SingleLiveEvent<Boolean>()
    val onPersonalFilterState : LiveData<Boolean> = _onPersonalFilterState

    //PopupWindow display method
    @SuppressLint("ClickableViewAccessibility")
    fun showPopupWindow(view: View) {


        //Create a View object yourself through inflater
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window, null)

        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        //Make Inactive Items Outside Of PopupWindow
        val focusable = true

        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        val checkBox = popupView.findViewById<CheckBox>(R.id.cb_popup_user_equipment)

        //Initialize the elements of our window, install the handler
        val popupTitle = popupView.findViewById<TextView>(R.id.tv_popup_title)
        popupTitle.text = view.context.getString(R.string.filter_equipments)
        val buttonApplyFilter = popupView.findViewById<Button>(R.id.btn_apply_filter)
        buttonApplyFilter.setOnClickListener { //As an example, display the message
            _onPersonalFilterState.value = checkBox.isChecked
            popupWindow.dismiss()
            Toast.makeText(view.context, "Filters applied", Toast.LENGTH_SHORT).show()
        }


        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }

        ArrayAdapter.createFromResource(
            view.context,
            R.array.length_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val lengthArray = view.context.resources.getStringArray(R.array.length_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val spinnerLength = popupView.findViewById<Spinner>(R.id.spinner_length)
            spinnerLength.adapter = adapter
            spinnerLength.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {

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

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }


    }
}