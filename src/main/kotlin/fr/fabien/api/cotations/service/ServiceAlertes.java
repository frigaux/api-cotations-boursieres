package fr.fabien.api.cotations.service;

import fr.fabien.api.cotations.restcontroller.dto.DtoAlerte;
import fr.fabien.api.cotations.restcontroller.dto.DtoAlerteAvecId;
import fr.fabien.api.cotations.restcontroller.exceptions.NotFoundException;
import fr.fabien.jpa.cotations.entities.Alerte;
import fr.fabien.jpa.cotations.entities.Valeur;
import fr.fabien.jpa.cotations.repository.RepositoryAlerte;
import fr.fabien.jpa.cotations.repository.RepositoryValeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ServiceAlertes {
    @Autowired
    private RepositoryValeur repositoryValeur;
    @Autowired
    private RepositoryAlerte repositoryAlerte;

    public List<DtoAlerteAvecId> alertes() {
        return repositoryAlerte.findAll()
                .stream()
                .map(DtoAlerteAvecId::new)
                .toList();
    }

    public List<DtoAlerteAvecId> alertesPourUneValeur(String ticker) {
        Valeur valeur = Optional.ofNullable(repositoryValeur.findByTicker(ticker))
                .orElseThrow(() -> new NotFoundException("ticker introuvable"));
        return repositoryAlerte.findByValeurOrderByDateLimiteDesc(valeur)
                .stream()
                .map(DtoAlerteAvecId::new)
                .toList();
    }

    public List<DtoAlerteAvecId> alertesPourPlusieursValeurs(Set<String> tickers) {
        return repositoryAlerte.queryByTickers(tickers)
                .stream()
                .map(DtoAlerteAvecId::new)
                .toList();
    }

    public DtoAlerteAvecId creerAlerte(DtoAlerte dtoAlerte) {
        Valeur valeur = Optional.ofNullable(repositoryValeur.findByTicker(dtoAlerte.ticker))
                .orElseThrow(() -> new NotFoundException("ticker introuvable"));
        Alerte alerte = dtoAlerte.creerEntite(valeur);
        alerte = repositoryAlerte.save(alerte);
        return new DtoAlerteAvecId(alerte);
    }

    public DtoAlerteAvecId modifierAlerte(Integer id, DtoAlerte dtoAlerte) {
        Valeur valeur = Optional.ofNullable(repositoryValeur.findByTicker(dtoAlerte.ticker))
                .orElseThrow(() -> new NotFoundException("ticker introuvable"));
        Alerte alerteExistante = repositoryAlerte.findById(id).map(alerte ->
                dtoAlerte.modifierEntite(valeur, alerte)
        ).orElseThrow(() -> new NotFoundException("alerte introuvable"));
        alerteExistante = repositoryAlerte.save(alerteExistante);
        return new DtoAlerteAvecId(alerteExistante);

    }

    public void supprimerAlerte(Integer id) {
        Alerte alerte = repositoryAlerte.findById(id)
                .orElseThrow(() -> new NotFoundException("alerte introuvable"));
        repositoryAlerte.delete(alerte);
    }
}
