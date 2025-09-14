package ru.language.translator.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.language.translator.entity.Translation
import ru.language.translator.entity.Word

@Repository
interface TranslationRepository: JpaRepository<Translation, Long> {

    fun findAllByOriginalWordInAndTranslatedWordIn(
        originalWord: List<Word>,
        translatedWord: List<Word>
    ): List<Translation>

    fun getAllByIdIn(idList: List<Long>): List<Translation>
}