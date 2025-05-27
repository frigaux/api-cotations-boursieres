package fr.fabien.api.cotations.configuration.impl

import fr.fabien.api.cotations.configuration.impl.cache.CustomCacheControl
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.WebContentInterceptor

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(
            WebContentInterceptor()
                .apply {
                    addCacheMapping(CustomCacheControl(), "/bourse/valeurs/**", "/bourse/cours/**")
                }
        );
    }
}