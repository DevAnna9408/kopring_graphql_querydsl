package kr.co.anna.api.resolver.query.user

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import kr.co.anna.domain.model.user.User
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserQuery (

    private val userRepository: UserRepository

        ) : GraphQLQueryResolver {

    /**
     * Http Method : Post
     * Header: 'Content-Type: application/json'
     * Body: { "query": "{ findAllUsers { oid, userId, name, email, role.. } }" }
     **/
    fun findAllUsers(): MutableList<User> = userRepository.findAll()

    /**
     * Http Method : Post
     * Header: 'Content-Type: application/json'
     * Body: { "query": "{ findByUserId(userId: \"someUserId\") { oid, userId, name, email, role.. } }" }

     **/
    fun findByUserId(userId: String): User? {
        val user = userRepository.findByUserId(userId)
        return if (user.isPresent) user.get()
        else null
    }

}
