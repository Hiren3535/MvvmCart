package com.hiren.practicaltest.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class ExtensionFunctions {

    /**
     * Return true if string is valid
     * */
    fun String?.isValidString(): Boolean {
        return this != null
                && !this.equals("", ignoreCase = true)
                && !this.equals("null", ignoreCase = true)
    }

    fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}