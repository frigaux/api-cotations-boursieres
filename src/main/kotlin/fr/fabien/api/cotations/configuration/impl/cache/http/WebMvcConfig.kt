package fr.fabien.api.cotations.configuration.impl.cache.http

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.WebContentInterceptor
import java.util.Calendar

@Configuration
class WebMvcConfig(@Value("\${cache.expired.hour}") private val heureCacheExpire: Int) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        KotlinLogging.logger {}.info("Timezone : ${Calendar.getInstance().getTimeZone().getDisplayName()}")
        registry.addInterceptor(
            WebContentInterceptor()
                .apply {
                    addCacheMapping(CustomCacheControl(heureCacheExpire), "/bourse/valeurs/**", "/bourse/cours/**")
                }
        )
    }
}