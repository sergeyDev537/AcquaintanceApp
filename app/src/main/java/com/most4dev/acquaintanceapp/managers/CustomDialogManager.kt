package com.most4dev.acquaintanceapp.managers

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import com.most4dev.acquaintanceapp.R
import java.util.*

class CustomDialogManager {

    companion object {

        fun createDateBirthday(context: Context, textViewBirthday: TextView) {

            val dateDialog = DatePickerDialog(
                context,
                R.style.MaterialCalendarTheme,
                { view, year, monthOfYear, dayOfMonth ->
                    val newDate: Calendar = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)

                    textViewBirthday.text = AgeManager.dateToString(newDate.time)
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            dateDialog.show()

        }
    }

}