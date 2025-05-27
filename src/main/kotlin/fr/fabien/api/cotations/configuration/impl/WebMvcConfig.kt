package fr.fabien.api.cotations.configuration.impl

import fr.fabien.api.cotations.configuration.impl.cache.CustomCacheControl
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.WebContentInterceptor

@Configuration
class WebMvcConfig(@Value("\${cache.expired.hour}") private val heureCacheExpire: Int) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(
            WebContentInterceptor()
                .apply {
                    addCacheMapping(CustomCacheControl(heureCacheExpire), "/bourse/valeurs/**", "/bourse/cours/**")
                }
        )
    }
}