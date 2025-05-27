package fr.fabien.api.cotations.configuration.impl.cache

import org.springframework.cache.Cache
import java.time.LocalDateTime

class CustomValueWrapper(private val value: Any?, private val dateTime: LocalDateTime) : Cache.ValueWrapper {

    override fun get(): Any? {
        return value
    }

    fun getDateTime(): LocalDateTime {
        return dateTime
    }
}