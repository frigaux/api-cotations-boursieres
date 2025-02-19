package fr.fabien.api.cotations.restcontroller.dto

import fr.fabien.jpa.cotations.Marche

data class DtoValeur(val ticker: String, val marche: Marche, val libelle: String)
