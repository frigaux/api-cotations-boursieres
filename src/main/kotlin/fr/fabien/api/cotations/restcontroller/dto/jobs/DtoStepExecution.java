package fr.fabien.api.cotations.restcontroller.dto.jobs;

import fr.fabien.jpa.springbatch.entities.StepExecution;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.format.DateTimeFormatter;

@Schema(description = "${dto.DtoStepExecution.description}")
public class DtoStepExecution {
    @Schema(description = "${dto.DtoStepExecution.field.nom}", example = "stepRecupererAbcLibelles", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    public String nom;
    @Schema(description = "${dto.DtoStepExecution.field.creation}", example = "2025-06-23T17:38:10.870063Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 27)
    public String creation;
    @Schema(description = "${dto.DtoStepExecution.field.debut}", example = "2025-06-23T17:38:10.870063Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 27)
    public String debut;
    @Schema(description = "${dto.DtoStepExecution.field.fin}", example = "2025-06-23T17:38:10.870063Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 27)
    public String fin;
    @Schema(description = "${dto.DtoStepExecution.field.statut}", example = "COMPLETED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 10)
    public String statut;
    @Schema(description = "${dto.DtoStepExecution.field.message}", example = "", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 2500)
    public String message;

    public DtoStepExecution(StepExecution stepExecution) {
        this.nom = stepExecution.stepName;
        this.creation = stepExecution.createTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.debut = stepExecution.startTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.fin = stepExecution.endTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.statut = stepExecution.status;
        if (!stepExecution.exitMessage.isEmpty())
            this.message = stepExecution.exitMessage;
    }
}