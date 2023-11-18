package kr.co.anna.api.controller.graphql.member

import kr.co.anna.domain.model.user.User
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

/**
 * Url: http://localhost:8080/graphql
 * Https Method: POST
 * Header: 'Content-Type : application/json'
 * **/
@Controller
class MemberGraphController (

    private val userRepository: UserRepository

        ) {

    /**
    {
        "query": "{ findAllUsers { oid userId name email } }"
    }
     **/
    @QueryMapping(value = "findAllUsers")
    fun findAllUsers(): List<User> = userRepository.findAll()
}
