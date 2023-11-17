package kr.co.anna.api.resolver.mutation.user

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import kr.co.anna.api.dto.user.SignUpIn
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserMutation (

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder

        ) : GraphQLMutationResolver {

    /**
     * Http Method : Post
     * Header: 'Content-Type: application/json'
     * Body
     {
        "query": "mutation ($signUpIn: SignUpIn!) { createUser(signUpIn: $signUpIn) { oid, userId, name, email, role } }",
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
    fun createUser(signUpIn: SignUpIn) = userRepository.save(signUpIn.toEntity(passwordEncoder))

}
