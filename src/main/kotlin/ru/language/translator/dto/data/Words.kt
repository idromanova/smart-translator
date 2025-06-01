package ru.language.translator.dto.data

import jakarta.persistence.*
import ru.language.translator.dto.data.enums.Language

@Entity
@Table(name = "words")
data class Word(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    val text: String,

    @Enumerated(value = EnumType.STRING)
    val language: Language
) {
    constructor() : this(0L, "", Language.RU)
}
