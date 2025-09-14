package ru.language.translator.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.language.translator.dto.rq.AddNewBookRq
import ru.language.translator.enums.Language
import ru.language.translator.service.data.archive.ArchiveService

@RestController
@RequestMapping("/api/v1/translator")
class TranslatorController {

    @Autowired
    lateinit var archiveService: ArchiveService

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    fun getAllBooks(): List<String> {
        return archiveService.getExistedBooks()
    }

    @PostMapping("books/add")
    @ResponseStatus(HttpStatus.OK)
    fun addNewBook(
        @RequestBody addNewBookRq: AddNewBookRq
    ) {
        val directoryName = archiveService.createNewDirectory(addNewBookRq.bookName)
        archiveService.createNewFileByLanguage(directoryName, Language.RU, addNewBookRq.originalBook)
        archiveService.createNewFileByLanguage(directoryName, Language.EN, addNewBookRq.translatedBook)
        archiveService.createNewTranslatedFile(directoryName, Language.RU, Language.EN, addNewBookRq)
    }
}