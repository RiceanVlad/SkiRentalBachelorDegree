package com.example.skirental.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.skirental.models.Equipment
import com.example.skirental.ui.activities.MainActivity
import com.google.android.gms.common.SignInButton
import com.google.firebase.storage.FirebaseStorage

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

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView?, imageUrl: String?) {
    // Load image with your image loading library ,like with Glide or Picasso or any of your favourite
    val storage = FirebaseStorage.getInstance()
    val gsReference = storage.getReferenceFromUrl("gs://skirentallicenta-ef1a0.appspot.com/Skis/$imageUrl.jpeg")

    imageView?.let { Glide.with(it.context).load(gsReference).into(imageView) }
}

