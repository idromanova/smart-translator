package ru.language.translator.repository.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.language.translator.dto.data.Translation
import ru.language.translator.dto.data.Word

@Repository
interface TranslationRepository: JpaRepository<Translation, Long> {

    fun findAllByOriginalWordInAndTranslatedWordIn(
        originalWord: List<Word>,
        translatedWord: List<Word>
    ): List<Translation>

    fun getAllByIdIn(idList: List<Long>): List<Translation>
}