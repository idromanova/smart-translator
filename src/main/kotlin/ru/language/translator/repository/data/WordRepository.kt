package ru.language.translator.repository.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.language.translator.dto.data.Word
import ru.language.translator.dto.data.enums.Language

@Repository
interface WordRepository: JpaRepository<Word, Long> {

    fun findAllByTextInAndLanguageIn(text: List<String>, languages: List<Language>): List<Word>

    fun getAllByIdIn(idList: List<Long>): List<Long>
}