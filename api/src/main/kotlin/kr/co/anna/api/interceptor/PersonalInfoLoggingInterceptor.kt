package kr.co.anna.api.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.anna.domain.model.log.QueryLog
import kr.co.anna.domain.repository.log.QueryLogRepository
import kr.co.anna.lib.security.SecurityUtil
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 개인정보 열람 정보 로깅
 * TODO: 프로젝트에 맞게 변경필요
 * CustomServletWrapperFilter 참고
 */
class PersonalInfoLoggingInterceptor(
    private val queryLogRepository: QueryLogRepository,
    private val om: ObjectMapper,
) : HandlerInterceptor {


    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val wrappingResponse = response as ContentCachingResponseWrapper
        try {
            val currentUserId = SecurityUtil.currentUserId()
            val requestURI = request.requestURI
            for (apiUri in queryTargetUris()) {
                if (request.method == HttpMethod.GET.name && requestURI.contains(apiUri) && response.status >= 200 && response.status < 300) {
                        val queryLog = QueryLog(
                            url = requestURI,
                            userId = currentUserId,
                            action = QueryLog.Action.QUERY,
                        )
                        queryLogRepository.save(
                            queryLog
                        )
                }
            }
            for (apiUri in downloadTargetUris()) {
                if (request.method == HttpMethod.GET.name && requestURI.contains(apiUri) && response.status >= 200 && response.status < 300) {
                    /*val jsonNode: JsonNode = om.readTree(wrappingResponse.contentAsByteArray)
                    val applIds = jsonNode.findValues("applId")*/
                    /* if (applIds.isNotEmpty()) {*/
                        val queryLog = QueryLog(
                            url = requestURI,
                            userId = currentUserId,
                            action = QueryLog.Action.DOWNLOAD,
                        )
                        queryLogRepository.save(
                            queryLog
                        )
                 /*   }*/
                }
            }
        } catch (t: Throwable) {
            log.error("Query Log 에러", t)
        }
        wrappingResponse.copyBodyToResponse()
    }


    fun queryTargetUris(): List<String> {
        return listOf(
            "/api/admin/users/all"
        )
    }

    fun downloadTargetUris(): List<String> {
        return listOf(
            "/api/admin/down/excel"
        )
    }

    private val log = LoggerFactory.getLogger(javaClass)
}
