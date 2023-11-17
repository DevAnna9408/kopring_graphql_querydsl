package kr.co.anna.api.config

import kr.co.anna.domain.repository.user.UserRepository
import kr.co.anna.lib.security.UserDetailsServiceImpl
import kr.co.anna.lib.security.jwt.JwtAuthFilter
import kr.co.anna.lib.security.jwt.JwtProcessor
import kr.co.anna.lib.security.jwt.JwtProperties
import kr.co.anna.lib.utils.MessageUtil
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 스프링 시큐리티 설정
 */
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfig(
    private val jwtProcessor: JwtProcessor,
    private val userRepository: UserRepository,
) : WebSecurityConfigurerAdapter() {

    /**
     * 규칙설정
     */
    override fun configure(http: HttpSecurity) {
        http
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf()
            .disable()
            .addFilterBefore(JwtAuthFilter(jwtProcessor), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(JwtAuthenticationEntryPoint())
            .accessDeniedHandler(CustomAccessDeniedHandler())
            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers(*swaggerAllowedList()).permitAll()
            .antMatchers(*actuatorAllowedList()).permitAll()
            .antMatchers(*devAllowedList()).permitAll()
            .antMatchers(HttpMethod.GET, *signAllowedList()).permitAll()
            .antMatchers(HttpMethod.POST, *signAllowedList()).permitAll()
            .antMatchers(HttpMethod.PUT, *signAllowedList()).permitAll()
            .antMatchers(HttpMethod.GET, *findAllowedList()).permitAll()
            .antMatchers(HttpMethod.PATCH, *changeAllowedList()).permitAll()
            .antMatchers(HttpMethod.GET, *checkAllowedList()).permitAll()
            .antMatchers(HttpMethod.GET, *commCodeAllowedList()).permitAll()
            .antMatchers(*sysAdminAllowedList()).hasAnyRole("SYS_ADMIN")
            .anyRequest().authenticated()
            .and()
            .headers()
            .addHeaderWriter(XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))

    }

    class JwtAuthenticationEntryPoint() : AuthenticationEntryPoint {
        override fun commence(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authException: AuthenticationException?
        ) {
            response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageUtil.getMessage("UNAUTHENTICATED_USER"))
        }
    }

    /**
     * 시큐리티 인가
     */
    class CustomAccessDeniedHandler() : AccessDeniedHandler {
        override fun handle(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            accessDeniedException: AccessDeniedException?
        ) {
            response?.sendError(HttpServletResponse.SC_FORBIDDEN,MessageUtil.getMessage("UNAUTHORIZED_ACCESS"))
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.addAllowedOriginPattern("http://localhost:3040")
        configuration.addAllowedOriginPattern("http://hello-gunpla-list.xyz")
        configuration.addAllowedOriginPattern("https://hello-gunpla-list.xyz")
        configuration.addAllowedMethod("*")
        configuration.addAllowedHeader("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L
        configuration.exposedHeaders = listOf("Content-Disposition")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    /**
     * 로그인 인증처리
     */
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(UserDetailsServiceImpl(userRepository))
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


    private fun commCodeAllowedList(): Array<String> {
        return arrayOf(
            "/api/api/common-code/**"
        )

    }

    private fun signAllowedList(): Array<String> {
        return arrayOf(
            "/api/sign-up",
            "/api/sign-in",
            "/api/find-password",
            "/api/answer-password",
            "/api/change-password/**",
        )
    }

    private fun findAllowedList(): Array<String> {
        return arrayOf(
            "/api/find/password"
        )
    }

    private fun changeAllowedList(): Array<String> {
        return arrayOf(
            "/api/{userId}/change-password-after-find"
        )
    }

    private fun swaggerAllowedList(): Array<String> {
        return arrayOf(
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
        )
    }

    private fun actuatorAllowedList(): Array<String> {
        return arrayOf(
            "/management/health",
            "/management/info",
        )
    }

    private fun devAllowedList(): Array<String> {
        return arrayOf(
            "/h2-console/**",
        )
    }

    private fun checkAllowedList(): Array<String> {
        return arrayOf(
            "/api/sign-up/check",
        )
    }

    private fun sysAdminAllowedList(): Array<String> {
        return arrayOf(
            "/api/admin/**",
        )
    }

}
