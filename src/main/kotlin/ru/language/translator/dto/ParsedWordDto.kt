package ru.language.translator.dto

data class ParsedObjectByTwoLangs(
    val firstLangText: String,
    val secondLangText: String,
    val complexity: Int,
    val firstLangPosition: Int,
    val secondLangPosition: Int
)

data class ParsedObjectByLang(
    val text: String,
    val complexity: Int,
    val position: Int
)