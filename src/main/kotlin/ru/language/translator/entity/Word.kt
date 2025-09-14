package ru.language.translator.entity

import jakarta.persistence.*
import ru.language.translator.enums.Language

@Entity
@Table(name = "words")
class Word(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "word_seq_gen")
    @SequenceGenerator(
        name = "word_seq_gen",
        sequenceName = "word_sequence",
        allocationSize = 20
    )
    var id: Long? = null,

    var text: String = "",

    @Enumerated(value = EnumType.STRING)
    val language: Language
) {
    constructor() : this(text = "", language = Language.RU)
}
