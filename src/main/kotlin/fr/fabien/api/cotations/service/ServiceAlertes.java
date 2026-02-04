package fr.fabien.api.cotations.service;

import fr.fabien.api.cotations.restcontroller.dto.DtoAlerteC;
import fr.fabien.api.cotations.restcontroller.exceptions.NotFoundException;
import fr.fabien.jpa.cotations.entities.Alerte;
import fr.fabien.jpa.cotations.entities.Valeur;
import fr.fabien.jpa.cotations.repository.RepositoryAlerte;
import fr.fabien.jpa.cotations.repository.RepositoryValeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceAlertes {
    @Autowired
    private RepositoryValeur repositoryValeur;
    @Autowired
    private RepositoryAlerte repositoryAlerte;

    public List<DtoAlerteC> alertes() {
        return repositoryAlerte.findAll()
                .stream()
                .map(DtoAlerteC::new)
                .toList();
    }

    public DtoAlerteC creerAlerte(DtoAlerteC dtoAlerte) {
        Valeur valeur = Optional.ofNullable(repositoryValeur.findByTicker(dtoAlerte.ticker))
                .orElseThrow(() -> new NotFoundException("ticker introuvable"));
        Alerte alerte = DtoAlerteC.versEntite(valeur, dtoAlerte);
        alerte = repositoryAlerte.save(alerte);
        return new DtoAlerteC(alerte);
    }
}
