package com.most4dev.acquaintanceapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.most4dev.acquaintanceapp.background.*
import com.most4dev.acquaintanceapp.managers.SharedPreferenceManager
import com.most4dev.acquaintanceapp.models.ChatUserModel
import com.most4dev.acquaintanceapp.models.PersonModel
import com.most4dev.acquaintanceapp.models.QuestionnaireModel

class AcquaintanceViewModel(application: Application): AndroidViewModel(application) {

    private var listQuestionnaire: MutableLiveData<List<QuestionnaireModel>>? = null
    private var listChatUser: MutableLiveData<ChatUserModel>? = null
    private var booleanWebView: MutableLiveData<Boolean>? = null

    fun getListQuestionnaire():
            LiveData<List<QuestionnaireModel>>? {
        if (listQuestionnaire == null) {
            listQuestionnaire = MutableLiveData()

        }
        val asyncInt =
            GetQuestionnaireApp(
                getApplication(),
                listQuestionnaire
            )
        asyncInt.execute(mutableMapOf<String, String>())
        return listQuestionnaire
    }

    fun getWebViewLoad(): LiveData<Boolean>{
        if (booleanWebView == null){
            booleanWebView = MutableLiveData()
        }
        val asyncInt =
            GetQuestionnaireAppDrop(
                getApplication(),
                booleanWebView
            )
        asyncInt.execute(mutableMapOf<String, String>())
        return booleanWebView as MutableLiveData<Boolean>
    }

    fun sendPerson(personModel: PersonModel){
        val asyncInt =
            SendPerson(
                personModel
            )
        asyncInt.execute(mutableMapOf<String, String>())
    }

    fun sendAbuse(name: String, theme: String, message: String){
        val asyncAcquaintanceAbuse =
            GetQuestionnaireAcquaintanceAppSend(
                name,
                theme,
                message
            )
        asyncAcquaintanceAbuse.execute(mutableMapOf<String, String>())
    }

    fun getListChatUser(questionnaireModel: QuestionnaireModel): LiveData<ChatUserModel>?{
        if (listChatUser == null) {
            listChatUser = MutableLiveData()
        }

        val sharedPreferenceManager = SharedPreferenceManager(getApplication())
        val chatUserModel = sharedPreferenceManager.getListMessageChat(questionnaireModel.cidChat)
        listChatUser!!.postValue(chatUserModel)
        return listChatUser
    }

}