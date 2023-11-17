package kr.co.anna.api.controller.user

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
    fun createMember(@RequestBody signUpIn: SignUpIn): ResponseEntity<UserOut> {
        val memberOut = userCommandService.createUser(signUpIn)
        return ResponseEntity.ok(memberOut)
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    fun login(@Valid @RequestBody signIn: SignInIn, bindingResult: BindingResult): ResponseEntity<SignInOut> {
        if (bindingResult.hasErrors()) throw InvalidException(MessageUtil.getMessage("INVALID_USER_INFO"), bindingResult)
        return ResponseEntity.ok(userLoginService.login(signIn))
    }

    @Operation(summary = "역할 목록 조회")
    @GetMapping("/roles")
    fun getRoles(): ResponseEntity<Map<String, List<EnumValue>?>> {
        return ResponseEntity.ok(enumMapper["ROLE"])
        // return ResponseEntity.ok(Role.values().map { EnumValue(it) })
    }

    @Operation(summary = "이메일, 아이디 중복체크")
    @GetMapping("/sign-up/check")
    fun isUse(
        @RequestParam type: String,
        @RequestParam value: String
    ): ResponseEntity<Boolean?>? {
        return ResponseEntity.ok(this.userCommandService.isUse(type, value))
    }
}
