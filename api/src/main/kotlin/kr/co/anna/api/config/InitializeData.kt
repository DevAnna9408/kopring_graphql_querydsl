package kr.co.anna.api.config

import kr.co.anna.domain.model.Role
import kr.co.anna.domain.model.user.User
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * 기초 데이터 생성
 * 2. InitData 클래스 실행으로  데이터 생성
 *   (기본 지식 1) CommandLineRunner 인터페이스를 구현하고 @Component 가 붙어있으므로 애플리케이션 기동 완료 후에 이 클래스가 자동 실행된다.
 *   (기본 지식 2) @Transactional 이 붙어 있으므로 이 클래스의 각 public 메서드 실행 도중 에러가 발생하면 해당 메서드에서 변경한 데이터는 모두 롤백 된다.
 *   (기본 지식 3) @Profile("initdb")이 붙어 있으므로 프로파일이 initdb 일 때만 실행된다.
 *   (기본 지식 4) 테이블 스키마는 InitData 클래스가 실행되기 전에 JPA 수준에서 생성/변경되며, InitData 클래스는 데이터 입력만 담당한다.
 *   1. application.yml 파일의 initdb 내용에 있는 jdbc url 이 정확한지 확인
 *   2. 프로파일을 initdb로 지정하고 애플리케이션 실행
 */
@Component
@Transactional
@Profile("initdb")
class InitializeData(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        /*initMember().map { println(it.role()) }*/
        initMember()
    }


    private fun initMember(): List<User> {
        if (userRepository.findAll().isEmpty()) {
            userRepository.saveAll(
                listOf(
                    User(
                        userId = "user",
                        name = "user",
                        email = "user@email.co.kr",
                        password = passwordEncoder.encode("User!@34"),
                        roles = listOf(Role.ROLE_USER)
                    ),
                    User(
                        userId = "userAdmin",
                        name = "admin",
                        email = "admin@email.co.kr",
                        password = passwordEncoder.encode("Admin!@34"),
                        roles = listOf(Role.ROLE_SYS_ADMIN, Role.ROLE_USER)
                    )
                )
            )
            return userRepository.findAll()
        }
        return emptyList()
    }


}
