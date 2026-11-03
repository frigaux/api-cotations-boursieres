package fr.fabien.api.cotations.restcontroller.dto.jobs;

import fr.fabien.jpa.springbatch.entities.JobExecution;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Schema(description = "${dto.DtoJobExecution.description}")
public class DtoJobExecution {
    @Schema(description = "${dto.DtoJobExecution.field.creation}", example = "2025-06-23T17:38:10.870063Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 27)
    public String creation;
    @Schema(description = "${dto.DtoJobExecution.field.debut}", example = "2025-06-23T17:38:10.870063Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 27)
    public String debut;
    @Schema(description = "${dto.DtoJobExecution.field.fin}", example = "2025-06-23T17:38:10.870063Z", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 27)
    public String fin;
    @Schema(description = "${dto.DtoJobExecution.field.statut}", example = "COMPLETED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 10)
    public String statut;
    @Schema(description = "${dto.DtoJobExecution.field.message}", example = "", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 2500)
    public String message;
    @Schema(description = "${dto.DtoJobExecution.field.parametres}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    public List<DtoJobExecutionParam> parametres;
    @Schema(description = "${dto.DtoJobExecution.field.etapes}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    public List<DtoStepExecution> etapes;

    public DtoJobExecution(JobExecution jobExecution) {
        this.creation = jobExecution.createTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.debut = jobExecution.startTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.fin = jobExecution.endTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.statut = jobExecution.status;
        if (!jobExecution.exitMessage.isEmpty())
            this.message = jobExecution.exitMessage;
        this.parametres = jobExecution.jobExecutionParams.stream().map(DtoJobExecutionParam::new).toList();
        this.etapes = jobExecution.stepExecutions.stream().map(DtoStepExecution::new).toList();
    }
}