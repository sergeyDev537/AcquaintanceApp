package com.most4dev.acquaintanceapp.managers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class AgeManager {

    companion object{

        private const val patterDate = "dd-MM-yyyy"

        fun comingOfAge(dateBirthday: String): Boolean {
            val dob: Calendar = Calendar.getInstance()
            val today: Calendar = Calendar.getInstance()
            val date = stringToDate(dateBirthday)

            if (date != null) {
                dob.time = date
            }

            var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--
            }

            val ageInt = age
            return ageInt >= 18
        }

        @SuppressLint("SimpleDateFormat")
        fun dateToString(date: Date): String{
            val format = SimpleDateFormat(patterDate)
            return format.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun stringToDate(stringDate: String): Date? {
            val format = SimpleDateFormat(patterDate)
            return format.parse(stringDate)
        }

    }

}