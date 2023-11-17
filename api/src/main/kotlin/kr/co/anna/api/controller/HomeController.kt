package kr.co.anna.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
@RestController
class HomeController(
    ) {
    @GetMapping("/")
    fun index(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello World!")
    }
}







