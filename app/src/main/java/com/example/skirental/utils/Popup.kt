package com.example.skirental.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LiveData
import com.example.skirental.R

class Popup {

    private val _onPersonalFilterState = SingleLiveEvent<Boolean>()
    val onPersonalFilterState : LiveData<Boolean> = _onPersonalFilterState

    //PopupWindow display method
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
        popupTitle.text = "some text"
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




    }
}