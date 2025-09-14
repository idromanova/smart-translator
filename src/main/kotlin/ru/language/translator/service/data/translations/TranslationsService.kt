package ru.language.translator.service.data.translations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.language.translator.dto.ParsedObjectByTwoLangs
import ru.language.translator.entity.Translation
import ru.language.translator.entity.Word
import ru.language.translator.repository.TranslationRepository

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

        translationList = translationList.distinctBy {
            listOf(it.originalWord?.id, it.translatedWord?.id)
        }.toMutableList()

        val translationListPairs = translationList.map { Pair(it.originalWord?.id, it.translatedWord?.id) }

        val existsTranslation = translationRepository.findAllByOriginalWordInAndTranslatedWordIn(
            translationList.mapNotNull { it.originalWord },
            translationList.mapNotNull { it.translatedWord }
        ).filter {
            translationListPairs.contains(Pair(it.originalWord?.id, it.translatedWord?.id))
        }

        val existsTranslationPairs = existsTranslation.map { Pair(it.originalWord?.id, it.translatedWord?.id) }

        val newTranslations = translationList.filter {
            !existsTranslationPairs.contains(Pair(it.originalWord?.id, it.translatedWord?.id))
        }
        if (newTranslations.isEmpty()) return existsTranslation

        val result = translationRepository.saveAll(newTranslations)
        return result + existsTranslation
    }

}