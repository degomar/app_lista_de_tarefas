package com.example.mytodolist.Extensions

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

val locale = Locale("pt","BR")

fun Date.format(): String{
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

var TextInputLayout.text: String
get() = editText?.text?.toString() ?: ""
set(value) {
    editText?.setText(value)
}