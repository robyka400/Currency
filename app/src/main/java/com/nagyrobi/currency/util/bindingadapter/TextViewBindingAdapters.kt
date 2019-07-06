package com.nagyrobi.currency.util.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:text")
fun TextView.setNumber(number: Number?) {
    text = String.format("%.2f", number).replace(".", ",")
}

@BindingAdapter("android:text")
fun TextView.setText(enum: Enum<*>?) {
    text = enum?.name
}