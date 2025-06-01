package ru.language.translator.service.replace

import org.springframework.stereotype.Component
import ru.language.translator.dto.ParsedObjectByTwoLangs
import kotlin.math.roundToInt

@Component
class ReplaceService {
    fun replace(objectList: List<ParsedObjectByTwoLangs>, knowledgeLevel: Int): List<Pair<String, String?>> {
        val groupedByComplexity = objectList.groupBy { it.complexity }

        return if (knowledgeLevel > 50) {
            val sortedList = objectList.sortedBy { it.secondLangPosition }
            val replaceWordCount = getReplacedWordCount(sortedList.count(), knowledgeLevel)
            val replacedWordList = getReplacedWordsList(groupedByComplexity, replaceWordCount)
            val replaceWordsPositions = replacedWordList.map { it.secondLangPosition }

            sortedList.map {
                if (replaceWordsPositions.contains(it.secondLangPosition))
                    Pair(it.secondLangText, it.firstLangText)
                else Pair(it.firstLangText, null)
            }
        } else {
            val replaceWordCount = getReplacedWordCount(objectList.count(), knowledgeLevel)
            val replacedWordList = getReplacedWordsList(groupedByComplexity, replaceWordCount)
            val replaceWordsPositions = replacedWordList.map { it.secondLangPosition }

            objectList.map {
                if (replaceWordsPositions.contains(it.secondLangPosition))
                    Pair(it.secondLangText, it.firstLangText)
                else Pair(it.firstLangText, null)
            }
        }
    }

    fun getReplacedWordsList(groupedByComplexity: Map<Int, List<ParsedObjectByTwoLangs>>, replaceWordCount:Int): List<ParsedObjectByTwoLangs> {
        var complexityLevel = 1
        val replacedWordList = mutableListOf<ParsedObjectByTwoLangs>()
        while (replacedWordList.count() != replaceWordCount) {
            val worldByComplexityGroup = groupedByComplexity[complexityLevel] ?: listOf()
            if (worldByComplexityGroup.count() < (replaceWordCount - replacedWordList.count())) {
                replacedWordList.addAll(worldByComplexityGroup)
                complexityLevel += 1
            }
            else {
                replacedWordList.addAll(
                    worldByComplexityGroup.shuffled()
                        .take(replaceWordCount - replacedWordList.count())
                )
            }
        }
        return replacedWordList
    }

    fun getReplacedWordCount(countOfWords: Int, knowledgeLevel: Int): Int {
        return (countOfWords / 100.0 * knowledgeLevel * 1.3).roundToInt()
    }
}