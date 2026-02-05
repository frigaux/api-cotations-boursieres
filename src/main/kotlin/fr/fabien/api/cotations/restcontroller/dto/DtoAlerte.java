package fr.fabien.api.cotations.restcontroller.dto;

import fr.fabien.jpa.cotations.entities.Alerte;
import fr.fabien.jpa.cotations.entities.Valeur;
import fr.fabien.jpa.cotations.enumerations.TypeAlerte;
import fr.fabien.jpa.cotations.enumerations.TypeNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Schema(description = "${dto.DtoAlerte.description}")
public class DtoAlerte {
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
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}", message = "format attendu (ISO8601) : YYYY-MM-DD")
    public String dateLimite;

    @Schema(description = "${dto.DtoAlerte.field.declenchementUnique}", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    public boolean declenchementUnique;

    @Schema(description = "${dto.DtoAlerte.field.notification}", example = "SYSTEME", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    public TypeNotification notification;

    public DtoAlerte() {
    }

    public DtoAlerte(String ticker, String libelle, TypeAlerte type, String expression, String dateLimite, boolean declenchementUnique, TypeNotification notification) {
        this.ticker = ticker;
        this.libelle = libelle;
        this.type = type;
        this.expression = expression;
        this.dateLimite = dateLimite;
        this.declenchementUnique = declenchementUnique;
        this.notification = notification;
    }

    public static Alerte versEntite(Valeur valeur, DtoAlerte dto) {
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
