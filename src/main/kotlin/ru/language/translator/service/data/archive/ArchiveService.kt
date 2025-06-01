package ru.language.translator.service.data.archive

import org.springframework.stereotype.Service
import java.io.File

@Service
class ArchiveService {
    val path = "src/main/resources/books"

    fun getExistedBooks(): List<String> {
        return File(path).list()?.toList() ?: listOf()
    }

    fun createNewDirectory(bookName:String): String {
        val directoryName = "$path/$bookName"
        val result = File(directoryName).createNewFile()
        return directoryName.takeIf { result } ?: "Ошибка"
    }

    fun createNewLocaleFile(directoryName: String, language: String): String {
        val fileName = "$path/$language.txt"
        val result = File(fileName).createNewFile()
        return directoryName.takeIf { result } ?: "Ошибка"
    }

    fun createNewTranslatedFile(directoryName: String, originLanguage: String, translatedLanguage: String): String {
        val fileName = "$path/${originLanguage}_$translatedLanguage.txt"
        val result = File(fileName).createNewFile()
        return directoryName.takeIf { result } ?: "Ошибка"
    }
}