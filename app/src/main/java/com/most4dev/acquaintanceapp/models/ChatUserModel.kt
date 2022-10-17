package com.most4dev.acquaintanceapp.models

import java.io.Serializable

data class ChatUserModel(
    val cid: String,
    var listMessage: List<MessageModel>
): Serializable