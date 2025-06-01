package ru.language.translator.dto.data

import jakarta.persistence.*
import ru.language.translator.dto.data.enums.Language


@Entity
@Table(name = "words")
data class Word(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val text: String,

    @Enumerated(value = EnumType.STRING)
    val language: Language
) {
    constructor() : this(text = "", language = Language.RU)
}
