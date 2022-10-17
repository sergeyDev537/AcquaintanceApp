package com.most4dev.acquaintanceapp.managers

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class TextWatcherManager {

    companion object{

        fun setTextWatcher(count: Int, view: TextInputEditText, stringError: String): TextWatcher{
            return object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (view.text!!.length < count){
                        view.error = stringError
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            }
        }

    }

}