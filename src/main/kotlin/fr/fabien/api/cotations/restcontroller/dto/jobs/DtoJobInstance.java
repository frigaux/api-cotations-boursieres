package fr.fabien.api.cotations.restcontroller.dto.jobs;

import fr.fabien.jpa.springbatch.entities.JobInstance;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "${dto.DtoJobInstance.description}")
public class DtoJobInstance {
    @Schema(description = "${dto.DtoJobInstance.field.nom}", example = "jobMajAbcLibelles", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = 100)
    public String nom;

    @Schema(description = "${dto.DtoJobInstance.field.executions}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    public List<DtoJobExecution> executions;

    public DtoJobInstance(JobInstance jobInstance) {
        this.nom = jobInstance.jobName;
        this.executions = jobInstance.jobExecutions.stream().map(DtoJobExecution::new).toList();
    }
}
