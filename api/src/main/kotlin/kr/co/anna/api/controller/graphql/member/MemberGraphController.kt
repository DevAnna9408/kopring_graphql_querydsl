package kr.co.anna.api.controller.graphql.member

import kr.co.anna.api.dto.user.UserOut
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberGraphController (

    private val userRepository: UserRepository

        ) {

    @QueryMapping(value = "findAllUsers")
    fun findAllUsers(): List<UserOut> = userRepository.findAll().map { UserOut.fromEntity(it) }
}
