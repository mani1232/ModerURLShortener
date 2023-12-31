package cc.worldmandia.moderURLShortener

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class AdviceController {

    @ResponseBody
    @ExceptionHandler(NotFoundException::class, AlreadyExistException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun defaultHandler(ex: Exception): String {
        return ex.message!!
    }

}