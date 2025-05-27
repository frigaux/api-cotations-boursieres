package fr.fabien.api.cotations.configuration.impl.cache

import fr.fabien.api.cotations.configuration.impl.cache.CustomCache.Companion.HEURE_CACHE_PERIME
import org.springframework.http.CacheControl
import java.time.LocalTime

class CustomCacheControl : CacheControl() {
    companion object {
        val HEURE_CACHE_PERIME_EN_S: Int = HEURE_CACHE_PERIME * 60 * 60
        val VINGT_QUATRE_HEURES_EN_S: Long = 24 * 60 * 60
    }

    override fun getHeaderValue(): String {
        // https://developer.mozilla.org/fr/docs/Web/HTTP/Reference/Headers/Cache-Control
        val time: Int = LocalTime.now().toSecondOfDay();
        // calcul de la durée de vie des données qui sont mises à jours quotidiennement (en semaine) à 20h
        if (time < HEURE_CACHE_PERIME_EN_S) {
            return "max-age=${HEURE_CACHE_PERIME_EN_S - time}";
        } else {
            return "max-age=${HEURE_CACHE_PERIME_EN_S - time + VINGT_QUATRE_HEURES_EN_S}";
        }
    }
}