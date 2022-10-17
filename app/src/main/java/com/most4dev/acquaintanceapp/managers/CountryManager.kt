package com.most4dev.acquaintanceapp.managers

import android.content.Context
import android.telephony.TelephonyManager

class CountryManager {

    companion object{

        fun getCountryISO(context: Context): String{
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.networkCountryIso
        }

    }

}