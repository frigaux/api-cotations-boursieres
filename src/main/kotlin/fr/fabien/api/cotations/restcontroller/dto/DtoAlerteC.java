package fr.fabien.api.cotations.restcontroller.dto;

import fr.fabien.jpa.cotations.entities.Alerte;
import fr.fabien.jpa.cotations.entities.Valeur;
import fr.fabien.jpa.cotations.enumerations.TypeAlerte;
import fr.fabien.jpa.cotations.enumerations.TypeNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Schema(description = "${dto.DtoAlerte.description}")
public class DtoAlerteC {
    @Schema(description = "${dto.DtoAlerte.field.id}", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Integer id;

    @Schema(description = "${dto.DtoAlerte.field.ticker}", example = "GLE", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "[A-Z0-9]{1,5}")
    @NotBlank
    @Size(max = 5)
    public String ticker;

    @Schema(description = "${dto.DtoAlerte.field.libelle}", example = "Plus bas sur 20 jours", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    public String libelle;

    @Schema(description = "${dto.DtoAlerte.field.type}", example = "PLUS_BAS", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    public TypeAlerte type;

    @Schema(description = "${dto.DtoAlerte.field.expression}", example = "CLOTURE(1)=PLUS_BAS(20)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    public String expression;

    @Schema(
            description = "${dto.DtoAlerte.field.dateLimite}", example = "2025-02-21", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    public String dateLimite;

    @Schema(description = "${dto.DtoAlerte.field.declenchementUnique}", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    public boolean declenchementUnique;

    @Schema(description = "${dto.DtoAlerte.field.notification}", example = "SYSTEME", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    public TypeNotification notification;

    public DtoAlerteC() {
    }

    public DtoAlerteC(Alerte alerte) {
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

    public static Alerte versEntite(Valeur valeur, DtoAlerteC dto) {
        return new Alerte(valeur,
                dto.libelle,
                dto.type,
                dto.expression,
                dto.dateLimite != null ? LocalDate.parse(dto.dateLimite, DateTimeFormatter.ISO_LOCAL_DATE) : null,
                dto.declenchementUnique,
                dto.notification,
                null);
    }
}
