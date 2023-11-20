package com.iwa.matching.service;

import com.iwa.matching.model.CandidatProfile;
import com.iwa.matching.model.CandidatProfile.Disponibilite;
import com.iwa.matching.model.CandidatProfile.Experience;
import com.iwa.matching.model.MatcherCandidat;
import com.iwa.matching.model.Offre;
import com.iwa.matching.repository.MatcherCandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {

    private final MatcherCandidatRepository matcherCandidatRepository;

    @Autowired
    public MatchingService(MatcherCandidatRepository matcherCandidatRepository) {
        this.matcherCandidatRepository = matcherCandidatRepository;
    }

    public List<MatcherCandidat> getAllMatching() {
        return matcherCandidatRepository.findAll();
    }

    public boolean matchCandidatWithOffre(CandidatProfile candidat, Offre offre) {
        // Vérifiez si la catégorie d'emploi correspond
        for (Experience experience : candidat.getExperiences()) {
            if (offre.getEmploi().equalsIgnoreCase(experience.getJobCategory())) {

                // Vérifiez si le candidat est disponible pendant la période de l'offre
                for (Disponibilite disponibilite : candidat.getDisponibilites()) {
                    if (disponibilite.getJobCategory().equalsIgnoreCase(offre.getEmploi()) &&
                            !offre.getDateDebut().after(disponibilite.getEndsAt()) &&
                            !offre.getDateFin().before(disponibilite.getStartsAt())) {

                        // Vérifiez si le lieu de disponibilité correspond à l'emplacement de l'offre
                        // Ici, nous supposons que l'emplacement de l'offre est stocké quelque part dans
                        // l'entité `Offre`
                        // et que la liste des lieux de disponibilité contient les lieux sous forme de
                        // chaînes de caractères.
                        for (String lieu : disponibilite.getPlaces()) {
                            if (lieu.equalsIgnoreCase(offre.getVille())) {
                                return true; // Match trouvé
                            }
                        }
                    }
                }
            }
        }
        return false; // Aucun match
    }

    // Méthode pour supprimer toutes les occurrences d'un candidat
    public void deleteCandidatOccurrences(String email) {
        List<MatcherCandidat> matches = matcherCandidatRepository.findByEmailCandidat(email);
        matcherCandidatRepository.deleteAll(matches);
    }
}
