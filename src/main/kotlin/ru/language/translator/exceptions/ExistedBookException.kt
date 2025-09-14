package ru.language.translator.exceptions

class ExistedBookException(
    override val message: String
): RuntimeException(message)