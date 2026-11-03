package fr.fabien.api.cotations.restcontroller.dto.jobs;

import fr.fabien.jpa.springbatch.entities.JobExecutionParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "${dto.DtoJobExecutionParam.description}")
public class DtoJobExecutionParam {
    @Schema(description = "${dto.DtoJobExecutionParam.field.nom}", example = "date", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    public String nom;
    @Schema(description = "${dto.DtoJobExecutionParam.field.type}", example = "java.time.LocalDate", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    public String type;
    @Schema(description = "${dto.DtoJobExecutionParam.field.valeur}", example = "2026-10-20", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 2500)
    public String valeur;

    public DtoJobExecutionParam(JobExecutionParam jobExecutionParam) {
        this.nom = jobExecutionParam.parameterName;
        this.type = jobExecutionParam.parameterType;
        this.valeur = jobExecutionParam.parameterValue;
    }
}
