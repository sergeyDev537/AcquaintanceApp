package com.most4dev.acquaintanceapp.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.most4dev.acquaintanceapp.R
import kotlinx.coroutines.*

class ChatViewModel(application: Application): AndroidViewModel(application) {

    private var listFirstMessage: MutableLiveData<List<String>>? = MutableLiveData()

    init {
        listFirstMessage!!.postValue(
            arrayListOf(
                "Welcome!","hi","How do you do?","hallo man)","Hello! Hi!","I’m glad to see you!",
                "I haven’t seen you for weeks.","How are you? Pleased to meet you!",
                "I аm all right.", "See you later.","I аm sorry.","I would like to speak to you...",
                "Hello) Do you mind if I smoke?", "hello","hallo))","hi! How’s everything?",
                "HI)","It has been a long time))","What have you been up to all these years?",
                "What’s new?","Hi man It's nice to put a face to a name.","I am glad to see you!!!",
                "Good afternoon)","How are you?","It’s been ages", "Alright????","G'day!","Wagwan",
                "Hey", "howdy hi!))","Hiya! What’s up?","Hey! There you are!!!","How are you doing?",
                "Hi! What’s cracking?", "Hi What’s new?", "Hello) What’s going on?",
                "Mmm Peek-a-boo!!", "Hi", "mister!!!",  "Men What’s happening?))",
                "mmm ok) How’s life?))", "Hello Sorry", "do I know you?", "Sorry",
                "do I know you? my frend", "Im wont you) I'm kidding))))", "mmm...",
                "hello...","hi mister))) How are you?", "Hello", "Hello))","Hi mister)"
            )
        )
    }

    val liveDataSecondMessage = liveData {
        val secondMessage = sendSecondMessage()
        emit(secondMessage)
    }

    val liveDataThirdMessage = liveData {
        val thirdMessage = sendThirdMessage()
        emit(thirdMessage)
    }

    private suspend fun sendSecondMessage(): String? {
        delay(2000)
        return listFirstMessage?.value?.random()
    }

    private suspend fun sendThirdMessage(): String?{
        delay(1500)
        return getApplication<Application>().getString(R.string.three_message)
    }

}