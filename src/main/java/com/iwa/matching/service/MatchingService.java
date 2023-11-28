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
            System.out.println("experience.getJobCategory(): " + experience.getJobCategory());
            System.out.println("offre.getTypeEmploi(): " + offre.getTypeEmploi().getNom());
            boolean offreTypeEmploiMatchedExperienceJobCategory = offre.getTypeEmploi().getNom().equalsIgnoreCase(experience.getJobCategory());
            System.out.println("offreTypeEmploiMatchedExperienceJobCategory: " + offreTypeEmploiMatchedExperienceJobCategory);
            if (offreTypeEmploiMatchedExperienceJobCategory) {
                // Vérifiez si le candidat est disponible pendant la période de l'offre
                for (Disponibilite disponibilite : candidat.getDisponibilites()) {
                    boolean offreTypeEmploiMatchedDisponibiliteJobCategory = disponibilite.getJobCategory().equalsIgnoreCase(offre.getTypeEmploi().getNom());
                    boolean offreDateDebutAfterDisponibiliteStartsAt = offre.getDateDebut().after(disponibilite.getStartsAt());
                    boolean offreDateFinBeforeDisponibiliteEndsAt = offre.getDateFin().before(disponibilite.getEndsAt());
                    boolean offreDateDebutBeforeOffreDateFin = offre.getDateDebut().before(offre.getDateFin());
                    System.out.println("offreTypeEmploiMatchedDisponibiliteJobCategory: " + offreTypeEmploiMatchedDisponibiliteJobCategory);
                    System.out.println("offreDateDebutAfterDisponibiliteStartsAt: " + offreDateDebutAfterDisponibiliteStartsAt);
                    System.out.println("offreDateFinBeforeDisponibiliteEndsAt: " + offreDateFinBeforeDisponibiliteEndsAt);
                    System.out.println("offreDateDebutBeforeOffreDateFin: " + offreDateDebutBeforeOffreDateFin);

                    if ((offreTypeEmploiMatchedDisponibiliteJobCategory) &&
                            (offreDateDebutAfterDisponibiliteStartsAt) &&
                                (offreDateFinBeforeDisponibiliteEndsAt) &&
                            (offreDateDebutBeforeOffreDateFin)) {

                        // Vérifiez si le lieu de disponibilité correspond à l'emplacement de l'offre
                        // Ici, nous supposons que l'emplacement de l'offre est stocké quelque part dans
                        // l'entité `Offre`
                        // et que la liste des lieux de disponibilité contient les lieux sous forme de
                        // chaînes de caractères.
                        for (String lieu : disponibilite.getPlaces()) {
                            if (lieu.equalsIgnoreCase(offre.getVille())) {
                                boolean isMatched = true;
                                System.out.println("Matched ? : " + isMatched);
                                return isMatched; // Match trouvé
                            }
                        }
                    }
                }
            }
        }
        return false; // Aucun match
    }

    public List<MatcherCandidat> getMatchesByOffreId(Long idOffre) {
        return matcherCandidatRepository.findByIdOffre(idOffre);
    }

    // Méthode pour supprimer toutes les occurrences d'un candidat
    public void deleteCandidatOccurrences(String email) {
        List<MatcherCandidat> matches = matcherCandidatRepository.findByEmail(email);
        matcherCandidatRepository.deleteAll(matches);
    }

    public void removeMatchesByOffreId(Long idOffre) {
        List<MatcherCandidat> matches = matcherCandidatRepository.findByIdOffre(idOffre);
        matcherCandidatRepository.deleteAll(matches);
    }
}
