package fr.fabien.api.cotations.restcontroller.dto;

import fr.fabien.jpa.cotations.entities.Alerte;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.format.DateTimeFormatter;

@Schema(description = "${dto.DtoAlerteAvecId.description}")
public class DtoAlerteAvecId extends DtoAlerte {
    @Schema(description = "${dto.DtoAlerteAvecId.field.id}", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Integer id;

    public DtoAlerteAvecId(Alerte alerte) {
        this.id = alerte.getId();
        this.ticker = alerte.getValeur().getTicker();
        this.libelle = alerte.getLibelle();
        this.type = alerte.getType();
        this.expression = alerte.getExpression();
        if (alerte.getDateLimite() != null) {
            this.dateLimite = alerte.getDateLimite().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        this.declenchementUnique = alerte.getDeclenchementUnique();
        this.notification = alerte.getNotification();
    }
}
