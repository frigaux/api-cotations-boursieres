package fr.fabien.api.cotations.service;

import fr.fabien.api.cotations.restcontroller.dto.DtoAlerteC;
import fr.fabien.api.cotations.restcontroller.dto.DtoAlerteR;
import fr.fabien.jpa.cotations.repository.RepositoryAlerte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAlertes {
    @Autowired
    private RepositoryAlerte repositoryAlerte;

    public List<DtoAlerteR> getAlertes() {
        return repositoryAlerte.findAll()
                .stream()
                .map(DtoAlerteR::create)
                .toList();
    }

}
