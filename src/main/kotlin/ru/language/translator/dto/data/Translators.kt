package ru.language.translator.dto.data

import jakarta.persistence.*

@Entity
@Table(name = "translations")
data class Translation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "original_word_id")
    val originalWord: Word?,

    @OneToOne
    @JoinColumn(name = "translated_word_id")
    val translatedWord: Word?
) {
    constructor() : this(0L, Word(), Word())
}