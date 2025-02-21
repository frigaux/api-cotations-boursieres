package fr.fabien.api.cotations.restcontroller.dto.dcpuv

data class DtoDcpuvCours(
    val date: String, val ouverture: Double, val plusHaut: Double,
    val plusBas: Double, val cloture: Double, val volume: Long,
    val moyennesMobiles: MutableList<Double>, val alerte: Boolean
)