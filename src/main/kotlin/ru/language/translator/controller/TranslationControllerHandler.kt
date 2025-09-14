package ru.language.translator.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.language.translator.dto.rs.ErrorRs
import ru.language.translator.exceptions.ExistedBookException

@RestControllerAdvice(assignableTypes = [TranslatorController::class])
class TranslationControllerHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        ExistedBookException::class
    )
    fun handleBadRequestException(ex: Exception): ErrorRs {
        return ErrorRs(ex.message ?: "Книга с таким названием уже существует")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleOtherException(ex: Exception): ErrorRs {
        return ErrorRs(ex.message ?: "Неизвестная ошибка")
    }

}