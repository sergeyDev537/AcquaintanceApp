package com.most4dev.acquaintanceapp.models

data class MessageModel(
    val content: String,
    val type: Int,
    val questionnaireModel: QuestionnaireModel
) {

    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
    }
}