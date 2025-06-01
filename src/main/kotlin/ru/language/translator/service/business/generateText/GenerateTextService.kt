package ru.language.translator.service.business.generateText

import org.springframework.stereotype.Service
import java.util.*

@Service
class GenerateTextService {

    fun generateText(replacedWords: List<Pair<String, String?>>): String {
        val strings: MutableList<String> = LinkedList()
        replacedWords.forEach {
            if (it.second == null)
                strings.add(it.first)
            else strings.add("@@${it.second}##${it.first}@@")
        }

        return java.lang.String.join(" ", strings)
    }
}