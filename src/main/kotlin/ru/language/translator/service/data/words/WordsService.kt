package ru.language.translator.service.data.words

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.language.translator.dto.ParsedObjectByTwoLangs
import ru.language.translator.entity.Word
import ru.language.translator.enums.Language
import ru.language.translator.repository.WordRepository

@Service
class WordsService {
    @Autowired
    private lateinit var wordRepository: WordRepository

    fun addNewWords(
        parsedWordsList: List<ParsedObjectByTwoLangs>,
        originalLanguage: Language,
        translatedLanguage: Language
    ): List<Word> {

        var wordsList = mutableListOf<Word>()
        parsedWordsList.forEach {
            wordsList.add(
                Word(
                    text = it.firstLangText,
                    language = originalLanguage
                )
            )
            wordsList.add(
                Word(
                    text = it.secondLangText,
                    language = translatedLanguage
                )
            )
        }

        wordsList = wordsList.distinctBy { listOf(it.text, it.language) }.toMutableList()

        val existsWords = wordRepository.findAllByTextInAndLanguageIn(
            wordsList.map { it.text },
            listOf(originalLanguage, translatedLanguage)
        )
        val existsWordsPairs = existsWords.map { Pair(it.text, it.language) }
        val newWords = wordsList.filterNot { existsWordsPairs.contains(Pair(it.text, it.language)) }
        if (newWords.isEmpty()) return existsWords

        val result = wordRepository.saveAll(newWords)
        return result + existsWords
    }
}