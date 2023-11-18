package kr.co.anna.api.controller.restapi.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.anna.api.dto.user.*
import kr.co.anna.api.service.command.user.UserCommandService
import kr.co.anna.api.service.command.user.UserLoginService
import kr.co.anna.domain._common.EnumMapper
import kr.co.anna.domain._common.EnumValue
import kr.co.anna.lib.error.InvalidException
import kr.co.anna.lib.security.SecurityUtil
import kr.co.anna.lib.utils.MessageUtil
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "회원 관리 API")
@RequestMapping("/api")
@RestController
class SignController(
    private val userCommandService: UserCommandService,
    private val userLoginService: UserLoginService,
    private val enumMapper: EnumMapper,
) {
    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    fun createUser(@RequestBody signUpIn: SignUpIn): ResponseEntity<UserOut> {
        val userOut = userCommandService.createUser(signUpIn)
        return ResponseEntity.ok(userOut)
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    fun login(@Valid @RequestBody signIn: SignInIn, bindingResult: BindingResult): ResponseEntity<SignInOut> {
        if (bindingResult.hasErrors()) throw InvalidException(MessageUtil.getMessage("INVALID_USER_INFO"), bindingResult)
        return ResponseEntity.ok(userLoginService.login(signIn))
    }
}
