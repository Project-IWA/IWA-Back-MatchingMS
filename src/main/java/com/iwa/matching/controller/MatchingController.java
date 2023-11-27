package com.iwa.matching.controller;

import com.iwa.matching.model.MatcherCandidat;
import com.iwa.matching.service.MatchingIntegrationService;
import com.iwa.matching.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    @Autowired
    private MatchingIntegrationService matchingIntegrationService;

    private final MatchingService matchingService;

    @Autowired
    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @PostMapping("/execute")
    public ResponseEntity<String> executeMatching() {
        matchingIntegrationService.performMatchingAndSaveResults();
        return ResponseEntity.ok("Le processus de matching a été exécuté.");
    }

    @GetMapping
    public ResponseEntity<List<MatcherCandidat>> getAllMatching() {
        List<MatcherCandidat> matching = matchingService.getAllMatching();
        return ResponseEntity.ok(matching);
    }

    // Endpoint pour récupérer les matchings pour une offre déterminée
    @GetMapping("/get-matches/{idOffre}")
    public ResponseEntity<List<MatcherCandidat>> getMatchesByOffreId(@PathVariable Long idOffre) {
        List<MatcherCandidat> matches = matchingService.getMatchesByOffreId(idOffre);
        return ResponseEntity.ok(matches);
    }

    // Endpoint pour supprimer toutes les occurrences d'un candidat spécifique
    @DeleteMapping("/delete-candidat/{email}")
    public ResponseEntity<?> deleteCandidatOccurrences(@PathVariable String email) {
        matchingService.deleteCandidatOccurrences(email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove-matches/{idOffre}")
    public ResponseEntity<?> removeMatchesByOffreId(@PathVariable Long idOffre) {
        matchingService.removeMatchesByOffreId(idOffre);
        return ResponseEntity.ok().build();
    }


}
