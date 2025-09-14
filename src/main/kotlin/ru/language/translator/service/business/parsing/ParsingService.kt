package ru.language.translator.service.business.parsing

import org.springframework.stereotype.Service
import ru.language.translator.dto.ParsedObjectByLang
import ru.language.translator.dto.ParsedObjectByTwoLangs
import ru.language.translator.dto.rq.AddNewBookRq

@Service
class ParsingService {
    fun parseText(addNewBookRq: AddNewBookRq): List<ParsedObjectByTwoLangs> {
        val firstObjectsList = addNewBookRq.originalBook.parseStringToDto()
        val secondObjectsList = addNewBookRq.translatedBook.parseStringToDto()

        val resultList = firstObjectsList.map { firstLangObject ->
            val secondLangObject = secondObjectsList.find { it!!.position == firstLangObject!!.position }
            ParsedObjectByTwoLangs(
                    firstLangText = firstLangObject!!.text,
                    secondLangText = secondLangObject!!.text,
                    complexity = firstLangObject.complexity,
                    firstLangPosition = firstLangObject.position,
                    secondLangPosition = secondLangObject.position
                )
        }
        return resultList
    }

    fun String.parseStringToDto(): List<ParsedObjectByLang?> {
        val pattern = Regex("""^(.*?)(?=#\d+@\d+$)#(\d+)@(\d+)$""")

        return this
            .removePrefix("@")
            .split(" @")
            .map { raw ->
                val match = pattern.matchEntire(raw) ?: return@map null

                val (text, complexityStr, positionStr) = match.destructured

                val complexity = complexityStr.toIntOrNull() ?: return@map null
                val position = positionStr.toIntOrNull() ?: return@map null

                ParsedObjectByLang(
                    text = text.removePrefix(positionStr),
                    complexity = complexity,
                    position = position,
                )
            }
    }
}