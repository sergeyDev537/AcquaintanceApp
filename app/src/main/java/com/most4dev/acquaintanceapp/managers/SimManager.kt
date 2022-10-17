package com.most4dev.acquaintanceapp.managers

import android.content.Context
import android.telephony.TelephonyManager

class SimManager {

    companion object{

        fun getSimOperator(context: Context): String{
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            return manager!!.simOperatorName
        }

    }

}