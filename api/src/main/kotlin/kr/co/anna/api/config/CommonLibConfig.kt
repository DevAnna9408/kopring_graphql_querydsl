package kr.co.anna.api.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * basePackages에 지정한 패키지 안에 @Component 가 붙어있는 클래스를 스프링 빈으로 읽어올 수 있도록 설정
 */
@Configuration
@ComponentScan(basePackages = ["kr.co.anna.lib"])
class CommonLibConfig {
}
