package kr.co.anna.api.controller.graphql.member

import kr.co.anna.api.dto.user.SignUpIn
import kr.co.anna.api.dto.user.UserUpdateIn
import kr.co.anna.domain.model.user.User
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import javax.validation.Valid

/**
 * Url: http://localhost:8080/graphql
 * Https Method: POST
 * Header: 'Content-Type : application/json'
 * **/
@Controller
@Transactional
class MemberGraphController (

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder

        ) {

    /**
    {
        "query": "{ findAllUsers { oid userId name email } }"
    }
     **/
    @QueryMapping
    fun findAllUsers(): List<User> = userRepository.findAll()

    /**
    {
        "query": "query ($userId: String!) {
            findByUserId(userId: $userId) { oid, userId, name, email } }",
                "variables": {
                    "userId": "userId"
                }
    }
     **/
    @QueryMapping
    fun findByUserId(
        @Argument userId: String
    ) = userRepository.findByUserId(userId).get()

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
    @MutationMapping
    fun createUser(
        @Argument signUpIn: SignUpIn
    ) = userRepository.save(signUpIn.toEntity(passwordEncoder))

    /**
    {
        "query": "mutation ($userOid: ID!) { deleteUserByUserOid(userOid: $userOid) }",
            "variables": {
                "userOid": "Some UserOid"
            }
    }
     **/
    @MutationMapping
    fun deleteUserByUserOid(
        @Argument userOid: Long
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
    @MutationMapping
    fun updateUserByUserOid(
        @Argument userOid: Long,
        @Argument userUpdateIn: UserUpdateIn
    ) {
        val user = userRepository.getByOid(userOid)
        user.updateWith(User.NewValue(
            name = userUpdateIn.name,
            email = userUpdateIn.email
        ))
    }
}
