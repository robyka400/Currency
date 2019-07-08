package com.nagyrobi.currency.util.bindingadapter

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

@BindingAdapter("double")
fun EditText.setDouble(number: Double?) {
     setText(String.format("%.2f", number))
}

@InverseBindingAdapter(attribute = "double", event = "android:textAttrChanged")
fun EditText.getDouble(): Double {
    return try {
        text.toString().toDouble()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        0.0
    }
}

@BindingAdapter("android:text")
fun TextView.setText(enum: Enum<*>?) {
    text = enum?.name
}