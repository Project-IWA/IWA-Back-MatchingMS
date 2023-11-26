package com.iwa.matching.service;

import com.iwa.matching.model.CandidatProfile;
import com.iwa.matching.model.Offre;
import com.iwa.matching.model.MatcherCandidat;
import com.iwa.matching.repository.MatcherCandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchingIntegrationService {

    @Autowired
    private MatcherCandidatRepository matcherCandidatRepository;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String OFFRES_API_URL = "http://localhost:8080/recrutements-app/api/offres";

    private final String CANDIDAT_API_URL = "http://localhost:3000/candidats";

    public void performMatchingAndSaveResults() {
        // 1. Récupérer les données des offres
        ResponseEntity<Offre[]> response = restTemplate.getForEntity(OFFRES_API_URL, Offre[].class);
        List<Offre> offres = Arrays.asList(response.getBody());

        // 2. Interroger l'API externe pour récupérer les données des candidats
        CandidatProfile[] candidatProfiles = restTemplate.getForObject(CANDIDAT_API_URL, CandidatProfile[].class);

        for (Offre offre : offres) {
            Set<CandidatProfile> matchedCandidats = List.of(candidatProfiles).stream()
                    .filter(candidat -> candidat.getEtat() == CandidatProfile.Etat.DISPONIBLE) // Prendre en compte que les candidats disponibles
                    .filter(candidat -> matchingService.matchCandidatWithOffre(candidat, offre))
                    .collect(Collectors.toSet());

            // 4. Enregistrer les correspondances valides dans la base de données
            saveMatchResults(matchedCandidats, offre);
        }
    }

    private void saveMatchResults(Set<CandidatProfile> matchedCandidats, Offre offre) {
        for (CandidatProfile candidat : matchedCandidats) {
            MatcherCandidat match = new MatcherCandidat();
            match.setIdOffre(offre.getIdOffre());
            match.setEmailCandidat(candidat.getEmail());

            matcherCandidatRepository.save(match); // Enregistrer le matching dans la base de données
        }
    }

    // planifier l'exécution du processus de matching toutes les 10 minutes
    @Scheduled(fixedRate = 600000) // 10 minutes en millisecondes
    public void scheduledMatching() {
        performMatchingAndSaveResults();
    }
}
