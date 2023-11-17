package kr.co.anna.api.config

import org.ehcache.event.CacheEvent
import org.ehcache.event.CacheEventListener
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

/**
 * 캐시 설정 클래스
 * 스프링 부트 기본인 EhCache 캐시 설정
 */
@Configuration
@EnableCaching
class CacheConfig {
}

class CacheLogger : CacheEventListener<Any, Any> {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun onEvent(cacheEvent: CacheEvent<out Any, out Any>) {
        log.info("Key: [${cacheEvent.key}] | EventType: [${cacheEvent.type}] | Old value: [${cacheEvent.oldValue}] | New value: [${cacheEvent.newValue}]")
    }
}

