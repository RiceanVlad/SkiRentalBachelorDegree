package com.example.skirental.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.skirental.R
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.google.android.gms.common.SignInButton
import com.google.android.material.progressindicator.LinearProgressIndicator
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

@BindingAdapter("setPrice")
fun TextView.setPrice(item: Equipment?) {
    item?.let {
        text = String.format(resources.getString(R.string.list_item_price_text, item.price.toString()))
    }
}

@BindingAdapter("setProgressBar")
fun setProgressBar(progressIndicator: LinearProgressIndicator?, item: Equipment?) {
    item?.let {
        progressIndicator?.progress = item.usage
        item.usage.let { usage ->
            when{
                usage < 10  -> {
                    progressIndicator?.setIndicatorColor(ContextCompat.getColor(progressIndicator.context, R.color.color_red))
                }
                usage < 50 -> {
                    progressIndicator?.setIndicatorColor(ContextCompat.getColor(progressIndicator.context, R.color.color_yellow))
                }
                else -> {
                    progressIndicator?.setIndicatorColor(ContextCompat.getColor(progressIndicator.context, R.color.color_green))
                }
            }
        }
    }
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView?, equipment: Equipment) {
    // Load image with your image loading library ,like with Glide or Picasso or any of your favourite
    val storage = FirebaseStorage.getInstance()
    val gsReference = storage.getReferenceFromUrl("gs://skirentallicenta-ef1a0.appspot.com/${equipment.type}/")
    if (imageView != null) {
        Glide.with(imageView.context)
            .load(R.drawable.loading_animation)
            .into(imageView)
    }
    gsReference.listAll()
        .addOnSuccessListener { listResult ->
            listResult.items.forEach { item ->
                if(item.toString().contains(equipment.id)) {
                    val extension = item.toString().substringAfterLast(".")
                    val imageRef = storage.getReferenceFromUrl("gs://skirentallicenta-ef1a0.appspot.com/${equipment.type}/${equipment.id}.$extension")
                    if (imageView != null) {
                        Glide.with(imageView.context)
                            .load(imageRef)
                            .into(imageView)
                    }
                }
            }
        }
}

@BindingAdapter("setListItemSize")
fun TextView.setListItemSize(equipment: Equipment) {
    text = when(equipment.type) {
        EquipmentType.SKI.string -> {
            String.format(resources.getString(R.string.list_item_cm_length, equipment.length.toString()))
        }
        EquipmentType.SKI_BOOTS.string -> {
            String.format(resources.getString(R.string.list_item_size_length, equipment.shoeSize.toString()))
        }
        else -> {
            equipment.length.toString()
        }
    }
}

@BindingAdapter("setPayPrice")
fun TextView.setPayPrice(price: Int) {
    text = String.format(resources.getString(R.string.tv_pay_total_price), price)
}
