package kr.co.anna.api.controller.graphql.user

import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import kr.co.anna.api.dto.user.SignUpIn
import kr.co.anna.api.dto.user.UserUpdateIn
import kr.co.anna.domain.model.user.User
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

/**
 * Url: http://localhost:8080/graphql
 * Https Method: POST
 * Header: 'Content-Type : application/json'
 * **/
@Controller
@Transactional
class UserGraphController (

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder

        ) : GraphQLQueryResolver, GraphQLMutationResolver {

    /**
    {
    "query": "{ findAllUsers { oid userId name email } }"
    }
     **/
    fun findAllUsers(): List<User> = userRepository.findAll()

    /**
    {
        "query": "query ($userId: String!) { getByUserId(userId: $userId) { oid, userId, name, email } }",
            "variables": {
                "userId": "userId"
            }
    }
     **/
    fun getByUserId(
        @RequestParam userId: String
    ) = userRepository.getByUserId(userId)

    /**
    {
        "query": "mutation ($signUpIn: SignUpIn!) { createUser(signUpIn: $signUpIn) { oid, userId, name, email } }",
            "variables": {
                "signUpIn": {
                    "userId": "newUserID",
                    "name": "New User",
                    "email": "newuser@example.com",
                    "password": "NewPassword"
            }
        }
    }
    **/

    fun createUser(
        @RequestBody signUpIn: SignUpIn
    ) = userRepository.save(signUpIn.toEntity(passwordEncoder))

    /**
    {
        "query": "mutation ($userOid: ID!) { deleteUserByUserOid(userOid: $userOid) }",
            "variables": {
                "userOid": "Some UserOid"
            }
    }
     **/
    fun deleteUserByUserOid(
        @RequestParam userOid: Long
    ) = userRepository.delete(userRepository.getByOid(userOid))

    /**
    {
        "query": "mutation updateUserByUserOid($userOid: ID!, $userUpdateIn: UserUpdateIn!) { updateUserByUserOid(userOid: $userOid, userUpdateIn: $userUpdateIn) { oid, userId, name, email } }",
            "variables": {
                "userOid": "1",
                "userUpdateIn": {
                    "name": "NewName",
                    "email": "newemail@example.com"
            }
        }
    }
    **/
    fun updateUserByUserOid(
        @RequestParam userOid: Long,
        @RequestBody userUpdateIn: UserUpdateIn
    ) {
        val user = userRepository.getByOid(userOid)
        user.updateWith(User.NewValue(
            name = userUpdateIn.name,
            email = userUpdateIn.email
        ))
    }
}
