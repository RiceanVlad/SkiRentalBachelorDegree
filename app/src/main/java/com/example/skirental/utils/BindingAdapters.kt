package com.example.skirental.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.skirental.R
import com.example.skirental.models.Equipment
import com.google.android.gms.common.SignInButton

@BindingAdapter("android:onClickGoogle")
fun bindSignInClick(button: SignInButton, method: () -> Unit) {
    button.setOnClickListener { method.invoke() }
}
@BindingAdapter("equipmentTitle")
fun TextView.setName(item: Equipment?) {
    item?.let {
        text = item.id
    }
}

@BindingAdapter("deteriorationPercentage")
fun TextView.setScore(item: Equipment?) {
    item?.let {
        text = item.length.toString()
    }
}

@BindingAdapter("imageEquipment")
fun ImageView.setPlayerImage(item: Equipment?) {
    item?.let {
        setImageResource(R.drawable.ic_cart)
    }
}

