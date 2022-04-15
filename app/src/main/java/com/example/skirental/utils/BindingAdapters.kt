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
@BindingAdapter("itemTitle")
fun TextView.setDescription(item: Equipment?) {
    item?.let {
        text = item.description
    }
}

@BindingAdapter("usagePercentage")
fun TextView.setUsage(item: Equipment?) {
    item?.let {
        text = item.usage.toString()
    }
}

@BindingAdapter("itemImage")
fun ImageView.setItemImage(item: Equipment?) {
    item?.let {
        setImageResource(R.drawable.ic_cart)
    }
}

