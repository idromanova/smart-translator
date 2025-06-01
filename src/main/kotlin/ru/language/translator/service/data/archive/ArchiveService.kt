package ru.language.translator.service.data.archive

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.language.translator.dto.data.enums.Language
import ru.language.translator.service.business.generateText.GenerateTextService
import ru.language.translator.service.business.parsing.ParsingService
import ru.language.translator.service.business.replace.ReplaceService
import ru.language.translator.service.data.translations.TranslationsService
import ru.language.translator.service.data.words.WordsService
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString

@Service
class ArchiveService {
    val path = "src/main/resources/books"

    @Autowired
    private lateinit var parsingService: ParsingService

    @Autowired
    private lateinit var replaceService: ReplaceService

    @Autowired
    private lateinit var wordsService: WordsService

    @Autowired
    private lateinit var translationsService: TranslationsService

    @Autowired
    private lateinit var generateTextService: GenerateTextService

    fun getExistedBooks(): List<String> {
        return File(path).list()?.toList() ?: listOf()
    }

    fun createNewDirectory(bookName: String): String {
        val directoryName = "$path/$bookName"
        val result = Files.createDirectory(Paths.get(directoryName))
        return result.pathString
    }

    fun createNewFileByLanguage(directoryName: String, language: Language, bookText: String) {
        val fileName = "$directoryName/${language.languageCode}.txt"
        val result = File(fileName).createNewFile()
        File(fileName).appendText(bookText)
    }

    fun createNewTranslatedFile(
        directoryName: String,
        originLanguage: Language,
        translatedLanguage: Language,
        originalBookText: String,
        translatedBookText: String,
    ) {
        val parsedText = parsingService.parseText(originalBookText, translatedBookText)
        val complexity = 20

        val words = wordsService.addNewWords(parsedText, originLanguage, translatedLanguage)
        val translations = translationsService.addNewTranslation(parsedText, words)

        val translatedText = replaceService.replace(parsedText, complexity)

        val generateText = generateTextService.generateText(translatedText)

        val fileName = "$directoryName/${originLanguage.languageCode}_${translatedLanguage.languageCode}.txt"
        val result = File(fileName).createNewFile()
        File(fileName).appendText(generateText)
    }
}