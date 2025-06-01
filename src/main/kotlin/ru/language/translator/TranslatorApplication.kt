package ru.language.translator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.language.translator.service.parsing.ParsingService
import ru.language.translator.service.replace.ReplaceService

@SpringBootApplication
class TranslatorApplication

fun main(args: Array<String>) {
	val applicationContext = runApplication<TranslatorApplication>(*args)

	val str1 = "@1Мистер#1@1 @2и#1@2 @3миссис#1@3 @4Дурсль#1@4 @5проживали#2@5 @6в доме#2@6 @7номер четыре#3@7 @8по Тисовой улице#4@8 @9и#1@9 @10всегда#2@10 @11с гордостью#3@11 @12заявляли#2@12 @13что#1@13 @14они —#1@14 @15слава богу —#4@15 @16абсолютно нормальные#3@16 @17люди.#1@17 @18Уж от кого-кого,#5@18 @19а от них#2@19 @20никак нельзя было ожидать#4@20 @21чтобы они попали#3@21 @22в какую-нибудь#2@22 @23странную ситуацию#3@23 @24или#1@24 @25загадочную ситуацию.#3@25 @26Мистер#1@26 @27и#1@27 @28миссис#1@28 @29Дурсль#1@29 @30весьма#2@30 @31неодобрительно относились#4@31 @32к любым#2@32 @33странностям,#3@33 @34загадкам#3@34 @35и прочей ерунде.#5@35"
	val str2 = "@1Mr#1@1 @2and#1@2 @3Mrs#1@3 @4Dursley#1@4 @5lived#2@5 @6in a house#2@6 @7number four#3@7 @8on Privet Drive#4@8 @9аnd#1@9 @10always#2@10 @11with pride#3@11 @12said#2@12 @13that#1@13 @14they —#1@14 @15thank goodness —#4@15 @16perfectly normal#3@16 @17people.#1@17 @18If anyone,#5@18 @19then not them#2@19 @20couldn’t be expected#4@20 @21to get involved#3@21 @22in any#2@22 @23strange situation#3@23 @24or#1@24 @25mysterious situation.#3@25 @26Mr#1@26 @27and#1@27 @28Mrs#1@28 @29Dursley#1@29 @30strongly#2@30 @31disapproved#4@31 @32of any#2@32 @33oddities,#3@33 @34mysteries#3@34 @35and other nonsense.#5@35"
	val str3 = "Мистер @and#и@ миссис Дурсль @проживали#lived@ в доме @номер четыре#number four@ по Тисовой улице @9и#1@9 @10всегда#2@10 @11с гордостью#3@11 @12заявляли#2@12 @13что#1@13 @14они —#1@14 @15слава богу —#4@15 @16абсолютно нормальные#3@16 @17люди.#1@17 @18Уж от кого-кого,#5@18 @19а от них#2@19 @20никак нельзя было ожидать#4@20 @21чтобы они попали#3@21 @22в какую-нибудь#2@22 @23странную ситуацию#3@23 @24или#1@24 @25загадочную ситуацию.#3@25 @26Мистер#1@26 @27и#1@27 @28миссис#1@28 @29Дурсль#1@29 @30весьма#2@30 @31неодобрительно относились#4@31 @32к любым#2@32 @33странностям,#3@33 @34загадкам#3@34 @35и прочей ерунде.#5@35"

	val parsingService = applicationContext.getBean("parsingService") as ParsingService
	val replaceService = applicationContext.getBean("replaceService") as ReplaceService

	val parsingResult = parsingService.parseText(str1, str2)
	val replacingResult = replaceService.replace(parsingResult, 20)
	println(replacingResult)
}
