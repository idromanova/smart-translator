package ru.language.translator.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.language.translator.dto.data.enums.Language
import ru.language.translator.service.data.archive.ArchiveService

@RestController
@RequestMapping("/api/v1/translator")
class TranslatorController {

    @Autowired
    lateinit var archiveService: ArchiveService

    @GetMapping("/books")
    fun getAllBooks(): List<String> {
        return archiveService.getExistedBooks()
    }

    @PostMapping("books/add")
    fun addNewBook(bookName: String, originalBook: String, translatedBook: String) {
        val directoryName = archiveService.createNewDirectory(bookName)
        archiveService.createNewFileByLanguage(directoryName, Language.RU, originalBook)
        archiveService.createNewFileByLanguage(directoryName, Language.EN, translatedBook)
        archiveService.createNewTranslatedFile(directoryName, Language.RU, Language.EN, originalBook, translatedBook)
    }
}