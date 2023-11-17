package kr.co.anna.domain.resolver.query.user

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import kr.co.anna.domain.model.user.User
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserResolver (

    private val userRepository: UserRepository

        ) : GraphQLQueryResolver {

    fun findAllUsers(): MutableList<User> = userRepository.findAll()


}
