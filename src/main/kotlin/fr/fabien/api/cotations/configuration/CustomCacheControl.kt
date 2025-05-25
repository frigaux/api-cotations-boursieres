package fr.fabien.api.cotations.configuration

import org.springframework.http.CacheControl
import java.time.LocalTime

class CustomCacheControl : CacheControl() {
    companion object {
        val vingtHeures: Long = 20 * 60 * 60;
        val quaranteQuatreHeures: Long = 44 * 60 * 60;
    }

    override fun getHeaderValue(): String? {
        // https://developer.mozilla.org/fr/docs/Web/HTTP/Reference/Headers/Cache-Control
        val time: Int = LocalTime.now().toSecondOfDay();
        // calcul de la durée de vie des données qui sont mises à jours quotidiennement (en semaine) à 20h
        if (time < vingtHeures) {
            return "max-age=${vingtHeures - time}";
        } else {
            return "max-age=${quaranteQuatreHeures - time}";
        }
    }
}