package com.currencyconverter.utils

import android.graphics.Color
import android.widget.EditText
import androidx.databinding.BindingAdapter

object ColorSetter {
    @BindingAdapter("setColor")
    @JvmStatic
    fun setEditTextColor(editText: EditText, string: String) {
        if(string == "0")
            editText.setTextColor(Color.GRAY)
        else
            editText.setTextColor(Color.BLACK)
    }
}