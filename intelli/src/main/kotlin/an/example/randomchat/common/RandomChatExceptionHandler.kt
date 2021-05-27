package an.example.randomchat.common

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice//하나의 빈이 아닌 전역에 작용하도록 하기위한 어노테이션
@RestController
class RandomChatExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(RandomChatException::class)
    fun handleRandomChatException(e: RandomChatException): ApiResponse{
        logger.error("API 에러", e)
        return ApiResponse.error(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse{
        logger.error("API 에러",e)
        return ApiResponse.error("알 수 없는 오류")
    }

}