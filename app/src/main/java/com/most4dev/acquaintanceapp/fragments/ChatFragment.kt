package com.most4dev.acquaintanceapp.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.most4dev.acquaintanceapp.R
import com.most4dev.acquaintanceapp.adapters.ChatAdapter
import com.most4dev.acquaintanceapp.managers.SharedPreferenceManager
import com.most4dev.acquaintanceapp.models.ChatUserModel
import com.most4dev.acquaintanceapp.models.MessageModel
import com.most4dev.acquaintanceapp.models.QuestionnaireModel
import com.most4dev.acquaintanceapp.viewModels.AcquaintanceViewModel
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.ArrayList
import androidx.lifecycle.LiveData
import com.most4dev.acquaintanceapp.viewModels.ChatViewModel


class ChatFragment : Fragment() {

    private lateinit var acquaintanceViewModel: AcquaintanceViewModel
    private lateinit var adapterChat: ChatAdapter
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var chatUserModel: ChatUserModel
    private var countMessage = 0
    private var questionnaireModel: QuestionnaireModel? = null
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        acquaintanceViewModel = ViewModelProvider(this)[AcquaintanceViewModel::class.java]
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()

        questionnaireModel = if (Build.VERSION.SDK_INT >= 33) {
            requireArguments().getSerializable(
                "QuestionnaireModelChat",
                QuestionnaireModel::class.java)
        } else {
            requireArguments().getSerializable("QuestionnaireModelChat")
        } as QuestionnaireModel

        if (questionnaireModel != null) {
            acquaintanceViewModel.getListChatUser(questionnaireModel!!)!!.observe(
                viewLifecycleOwner
            ) {
                countMessage = it.listMessage.size
                chatUserModel = it
                adapterChat.setItems(it.listMessage)
            }
        }


        imageViewSendMessage.setOnClickListener {
            if (editTextMessage.text.isNotEmpty()) {
                sendMessage(editTextMessage.text.toString(), MessageModel.TYPE_SENT)
                editTextMessage.setText("")

                if ((adapterChat.getItems() as MutableList).size == 1) {
                    sendMessagesReceived(chatViewModel.liveDataSecondMessage)
                    countMessage++
                } else if ((adapterChat.getItems() as MutableList).size == 3) {
                    sendMessagesReceived(chatViewModel.liveDataThirdMessage)
                }
            }
        }
    }

    private fun sendMessagesReceived(liveData: LiveData<String?>) {
        liveData.observe(viewLifecycleOwner) {
            sendMessage(it!!, MessageModel.TYPE_RECEIVED)
        }
    }

    private fun sendMessage(message: String, typeMessage: Int){
        val listMessage = adapterChat.getItems() as MutableList
        listMessage.add(
            MessageModel(
                message,
                typeMessage,
                questionnaireModel!!
            )
        )
        adapterChat.setItems(listMessage)
        chatUserModel.listMessage = listMessage
        sharedPreferenceManager.setListMessageChat(chatUserModel, chatUserModel.cid)
    }

    private fun createAdapter() {
        adapterChat = ChatAdapter(
            requireContext(),
            ArrayList()
        )

        val recyclerViewChatHome: RecyclerView = recyclerViewChat
        recyclerViewChatHome.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewChatHome.adapter = adapterChat
    }
}