package fr.fabien.api.cotations.configuration.impl.cache.spring

import org.springframework.cache.Cache
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class CustomCache(private val name: String, private val heureCacheExpire: Int) : Cache {
    private var cache: ConcurrentMap<Any, CustomValueWrapper> = ConcurrentHashMap()

    override fun getName(): String {
        return name
    }

    override fun getNativeCache(): Any {
        return this
    }

    override fun get(key: Any): Cache.ValueWrapper? {
        return cache.get(key)
            ?.let {
                var jour = LocalDateTime.now()
                if (LocalTime.now().hour < heureCacheExpire) {
                    jour = jour.minusDays(1)
                }
                val dateCacheExpire2 = LocalDateTime.of(jour.year, jour.month, jour.dayOfMonth, heureCacheExpire, 0)
                if (it.getDateTime() > dateCacheExpire2) it else null
            }
    }

    override fun <T : Any?> get(key: Any, type: Class<T?>?): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> get(key: Any, valueLoader: Callable<T?>): T? {
        TODO("Not yet implemented")
    }

    override fun put(key: Any, value: Any?) {
        cache.put(key, CustomValueWrapper(value, LocalDateTime.now()))
    }

    override fun evict(key: Any) {
        cache.remove(key)
    }

    override fun clear() {
        cache = ConcurrentHashMap()
    }
}