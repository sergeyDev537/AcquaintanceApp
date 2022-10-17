package com.most4dev.acquaintanceapp.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.most4dev.acquaintanceapp.models.ChatUserModel
import com.most4dev.acquaintanceapp.models.PersonModel
import com.google.gson.Gson

class SharedPreferenceManager(context: Context) {

    private var sharedPreferences: SharedPreferences
    private var editorSharedPreferences: SharedPreferences.Editor

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        editorSharedPreferences = sharedPreferences.edit()
        editorSharedPreferences.apply()
    }

    fun savePersonModel(personModel: PersonModel){
        val gson = Gson()
        val json = gson.toJson(personModel)
        editorSharedPreferences.putString("PersonModelKey", json)
        editorSharedPreferences.commit()
    }

    fun getPersonModel(): PersonModel {
        val gson = Gson()
        val json: String = sharedPreferences.getString("PersonModelKey", "")!!
        return gson.fromJson(json, PersonModel::class.java)
    }

    fun setAgeOfComing(boolean: Boolean){
        editorSharedPreferences.putBoolean("AgeOfComing", boolean)
        editorSharedPreferences.commit()
    }

    fun getAgeOfComing(): Boolean{
        return sharedPreferences.getBoolean("AgeOfComing", true)
    }

    fun setCompleteRegister(boolean: Boolean){
        editorSharedPreferences.putBoolean("CompleteRegister", boolean)
        editorSharedPreferences.commit()
    }

    fun getCompleteRegister(): Boolean{
        return sharedPreferences.getBoolean("CompleteRegister", false)
    }

    fun setListMessageChat(chatUserModel: ChatUserModel, cidChat: String){
        val gson = Gson()
        val json = gson.toJson(chatUserModel)
        editorSharedPreferences.putString(cidChat, json)
        editorSharedPreferences.commit()
    }

    fun getListMessageChat(cidChat: String): ChatUserModel{
        val gson = Gson()
        val json: String = sharedPreferences.getString(cidChat, "")!!
        return if (json != ""){
            gson.fromJson(json, ChatUserModel::class.java)
        } else{
            ChatUserModel(cidChat, ArrayList())
        }

    }

}