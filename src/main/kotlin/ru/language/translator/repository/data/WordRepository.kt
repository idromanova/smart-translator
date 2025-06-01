package ru.language.translator.repository.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.language.translator.dto.data.Word

@Repository
interface WordRepository: JpaRepository<Word, Long> {
}