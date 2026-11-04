package fr.fabien.api.cotations.service;

import fr.fabien.api.cotations.restcontroller.dto.jobs.DtoJobInstance;
import fr.fabien.jpa.springbatch.entities.JobInstance;
import fr.fabien.jpa.springbatch.repositories.RepositoryJobInstance;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class ServiceJobs {
    @Autowired
    private RepositoryJobInstance repositoryJobInstance;

    public List<DtoJobInstance> dernieresExecutions(Set<String> jobNames) {
        return repositoryJobInstance.findLastJobsByName(jobNames, Limit.of(jobNames.size()))
                .stream()
                .filter(filterJobInstance())
                .map(DtoJobInstance::new)
                .toList();
    }

    private static @NonNull Predicate<JobInstance> filterJobInstance() {
        return jobInstance -> jobInstance.jobExecutions.stream()
                .noneMatch(jobExecution -> jobExecution.endTime == null);
    }
}
