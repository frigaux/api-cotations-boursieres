package fr.fabien.api.cotations.configuration.impl.cache

import org.springframework.cache.Cache
import org.springframework.cache.support.AbstractCacheManager
import org.springframework.context.annotation.Configuration

@Configuration
class CustomCacheManager : AbstractCacheManager() {
    override fun loadCaches(): Collection<Cache> {
        return emptyList()
    }

    override fun decorateCache(cache: Cache): Cache {
        return cache
    }

    override fun getMissingCache(name: String): Cache {
        return CustomCache(name)
    }
}