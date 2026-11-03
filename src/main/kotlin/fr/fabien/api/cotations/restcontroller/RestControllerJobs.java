package fr.fabien.api.cotations.restcontroller;

import fr.fabien.api.cotations.restcontroller.dto.jobs.DtoJobInstance;
import fr.fabien.api.cotations.service.ServiceJobs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static fr.fabien.api.cotations.restcontroller.RestControllerAlertes.SECURITY_SCHEME_NAME;

@Tag(name = "${api.jobs.name}")
@RestController
@RequestMapping("bourse/jobs")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class RestControllerJobs {
    public static final String SECURITY_SCHEME_NAME = "JWT Bearer Authentication";

    @Autowired
    private ServiceJobs serviceJobs;

    @Operation(summary = "${api.jobs.operation.derniereExecution.summary}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "${api.jobs.operation.derniereExecution.response[200]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = DtoJobInstance.class))
                                    )
                            }
                    )
            }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    private List<DtoJobInstance> derniereExecution(
            @Parameter(
                    description = "${api.jobs.operation.derniereExecution.parameter.jobNames}",
                    required = true,
                    example = "jobMajAbcLibelles, jobMajAbcCotations, jobMajBoursoramaCours, jobConvertirEnValeurCours, jobCalculerMoyennes"
            )
            @RequestParam Set<String> jobNames) {
        return serviceJobs.derniereExecution(jobNames);
    }
}
