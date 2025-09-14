package ru.language.translator.entity

import jakarta.persistence.*

@Entity
@Table(name = "translations")
class Translation(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_seq")
    @SequenceGenerator(name = "translation_seq",
        sequenceName = "translation_sequence",
        initialValue = 1, allocationSize = 20)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "original_word_id")
    val originalWord: Word?,

    @OneToOne
    @JoinColumn(name = "translated_word_id")
    val translatedWord: Word?
) {
    constructor() : this(0L, Word(), Word())
}