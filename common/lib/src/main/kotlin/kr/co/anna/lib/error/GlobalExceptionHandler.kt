package kr.co.anna.lib.error

import kr.co.anna.domain.exception.DomainEntityNotFoundException
import kr.co.anna.domain.exception.NotAllowedException
import kr.co.anna.lib.security.AuthUser
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.NestedRuntimeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.InvalidParameterException

@RestControllerAdvice
class GlobalExceptionHandler(
) {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorBody> {
        log(e,e.javaClass.simpleName)
        val errorBody =
            ErrorBody(
                message = e.message ?: "메시지 없는 IllegalArgumentException 이 발생했습니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
                ,e.javaClass.simpleName
            )
        return ResponseEntity(errorBody, HttpStatus.BAD_REQUEST)
    }

    /**
     * 인가처리
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(
        e: AccessDeniedException
    ): ResponseEntity<ErrorBody> {
        log(e,e.javaClass.simpleName)
        val errorBody =
            ErrorBody(
                message = e.message ?: "허가되지 않은 접근입니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
                ,e.javaClass.simpleName
            )
        return ResponseEntity(errorBody, HttpStatus.FORBIDDEN)
    }

    /**
     * 인증처리
     */
    @ExceptionHandler(
        JwtUnauthenticatedAccessException::class,
        UnauthenticatedAccessException::class,
        BadCredentialsException::class
    )
    fun handleBadCredentialsException(
        e: RuntimeException
    ): ResponseEntity<ErrorBody> {
        log(e,e.javaClass.simpleName)
        val errorBody =
            ErrorBody(
                message = e.message ?: "로그인 정보가 올바르지 않습니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
                ,e.javaClass.simpleName
            )
        return ResponseEntity(errorBody, HttpStatus.UNAUTHORIZED)
    }


    /**
     * 비즈니스 정의 익셉션
     * 기능제한
     */
    @ExceptionHandler(NotAllowedException::class)
    fun handleNotAllowedException(
        e: NotAllowedException
    ): ResponseEntity<ErrorBody> {
        log(e,e.javaClass.simpleName)
        val errorBody =
            ErrorBody(
                message = e.message ?: "허용되지 않는 기능입니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
                ,e.javaClass.simpleName
            )
        return ResponseEntity(errorBody, HttpStatus.FORBIDDEN)
    }


    /**
     * 도메인 익셉션
     */
    @ExceptionHandler(DomainEntityNotFoundException::class)
    fun handleDomainEntityNotFoundException(
        e: DomainEntityNotFoundException
    ): ResponseEntity<ErrorBody> {
        log(e,e.entityClazz.simpleName)
        val errorBody =
            ErrorBody(
                message = e.message ?: "해당정보를 찾을 수 없습니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
                ,e.entityClazz.simpleName
            )
        return ResponseEntity(errorBody, HttpStatus.NOT_FOUND)
    }


    @ExceptionHandler(NestedRuntimeException::class)
    fun handleNestedRuntimeException(e: NestedRuntimeException): ResponseEntity<ErrorBody> {
        log(e,e.javaClass.simpleName)
        val errorBody =
            ErrorBody(
                message = e.rootCause?.message ?: e.message ?: "메시지 없는 NestedRuntimeException 이 발생했습니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
                ,e.javaClass.simpleName
            )
        return ResponseEntity(errorBody, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * 밸리데이션 값 검증 에러
     */
    @ExceptionHandler(InvalidException::class, InvalidParameterException::class)
    fun handleInvaliduserException(e: InvalidException): ResponseEntity<ErrorBody> {
        log(e,e.javaClass.simpleName)
        val errorBody =
            ErrorBody(
                message = e.message ?: "메시지 없는 InvalidException 이 발생했습니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString(),
                e.javaClass.simpleName,
                errors = e.errors,
            )
        return ResponseEntity(errorBody, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorBody> {
        log(e,e.javaClass.simpleName)
        val errorBody =
            ErrorBody(
                message = e.message ?: "메시지 없는 Exception 이 발생했습니다.",
                requestUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()
                ,e.javaClass.simpleName
            )
        return ResponseEntity(errorBody, HttpStatus.INTERNAL_SERVER_ERROR)
    }


    private fun setUserId() {
        val authUser = SecurityContextHolder.getContext().authentication.principal as? AuthUser
        MDC.put("userId", authUser?.userId)
    }

    private fun log(
        e: Exception,
        className : String?
    ) {
        setUserId()
        log.error("$className | $e")
        MDC.clear()
    }

    private val log = LoggerFactory.getLogger(javaClass)
}
