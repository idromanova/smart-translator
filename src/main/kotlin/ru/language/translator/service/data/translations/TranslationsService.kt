package ru.language.translator.service.data.translations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.language.translator.dto.business.ParsedObjectByTwoLangs
import ru.language.translator.dto.data.Translation
import ru.language.translator.dto.data.Word
import ru.language.translator.repository.data.TranslationRepository

@Service
class TranslationsService {

    @Autowired
    private lateinit var translationRepository: TranslationRepository

    fun addNewTranslation(parsedWordsList: List<ParsedObjectByTwoLangs>, wordList: List<Word>): List<Translation> {

        var translationList = parsedWordsList.map {
            Translation(
                originalWord = wordList.find { word -> word.text == it.firstLangText },
                translatedWord = wordList.find { word -> word.text == it.secondLangText },
            )
        }

        translationList = translationList.distinct().toMutableList()

        val existsTranslation = translationRepository.findAllByOriginalWordInAndTranslatedWordIn(
            translationList.mapNotNull { it.originalWord },
            translationList.mapNotNull { it.translatedWord }
        ).filter {
            translationList.contains(it)
        }

        val newTranslations = translationList.filter { !existsTranslation.contains(it) }
        val result = translationRepository.saveAllAndFlush(newTranslations)
        return result + existsTranslation
    }

}