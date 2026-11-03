package fr.fabien.api.cotations.service;

import fr.fabien.api.cotations.restcontroller.dto.jobs.DtoJobInstance;
import fr.fabien.jpa.springbatch.repositories.RepositoryJobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServiceJobs {
    @Autowired
    private RepositoryJobInstance repositoryJobInstance;

    public List<DtoJobInstance> derniereExecution(Set<String> jobNames) {
        return repositoryJobInstance.findLastJobsByName(jobNames, Limit.of(jobNames.size()))
                .stream()
                .map(DtoJobInstance::new)
                .toList();
    }
}
