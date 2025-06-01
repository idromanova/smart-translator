package ru.language.translator.service.data.words

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.language.translator.dto.business.ParsedObjectByTwoLangs
import ru.language.translator.dto.data.Word
import ru.language.translator.dto.data.enums.Language
import ru.language.translator.repository.data.WordRepository

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
                    language = Language.RU
                )
            )
            wordsList.add(
                Word(
                    text = it.secondLangText,
                    language = Language.RU
                )
            )
        }

        wordsList = wordsList.distinct().toMutableList()

        val existsWords = wordRepository.findAllByTextInAndLanguageIn(
            wordsList.map { it.text },
            listOf(originalLanguage, translatedLanguage)
        )
        val newWords = wordsList.filter { !existsWords.contains(it) }
        val result = wordRepository.saveAllAndFlush(newWords)
        return result + existsWords
    }
}