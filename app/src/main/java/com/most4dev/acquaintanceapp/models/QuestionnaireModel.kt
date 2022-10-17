package com.most4dev.acquaintanceapp.models

import java.io.Serializable

data class QuestionnaireModel(
    val id: String,
    val name: String,
    val age: String,
    val description: String,
    val image: String,
    val cidChat: String
): Serializable