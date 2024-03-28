package ru.practicum.android.diploma.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onChange(callback: (String) -> Unit){
    val textWatcher = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
            callback.invoke(text.toString())
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
    this.addTextChangedListener(textWatcher)
}
