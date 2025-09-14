package ru.language.translator.dto.rq

data class AddNewBookRq(
    val bookName: String,
    val originalBook: String,
    val translatedBook: String
)
