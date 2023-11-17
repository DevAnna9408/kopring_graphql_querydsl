package kr.co.anna.lib

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

interface LocationProvider {

    fun createdUri(id: Long? = null): URI {
        val relativePath: String = translatedPath(ServletUriComponentsBuilder.fromCurrentRequestUri().build().path!!)
        return if (id == null) URI.create(relativePath) else URI.create("$relativePath/$id")
    }

    fun uri(): URI {
        return URI.create(translatedPath(ServletUriComponentsBuilder.fromCurrentRequestUri().build().path!!))
    }

    fun translatedPath(relativePath: String): String {
        return relativePath.replace("command", "query")
    }
}
