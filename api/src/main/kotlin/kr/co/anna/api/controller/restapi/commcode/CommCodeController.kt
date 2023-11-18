package kr.co.anna.api.controller.restapi.commcode

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.anna.domain._common.EnumMapper
import kr.co.anna.domain._common.EnumValue
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "commonCode-controller", description = "공통 코드성 조회 컨트롤러 ")
@RequestMapping("/api/common-code")
@RestController
class CommCodeController(
    private val enumMapper: EnumMapper,
    ) {

    @Operation(summary = "enumMapper에 등록된 enum 목록 조회")
    @GetMapping("/enums/{names}")
    fun findByEnums(@PathVariable("names") names: String): ResponseEntity<Map<String, List<EnumValue>?>> {
        return ResponseEntity.ok(enumMapper[names])
    }


}







