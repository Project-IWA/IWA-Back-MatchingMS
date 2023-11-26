package com.iwa.matching.repository;

import com.iwa.matching.model.MatcherCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatcherCandidatRepository extends JpaRepository<MatcherCandidat, Long> {
    // Méthodes personnalisées
    List<MatcherCandidat> findByEmailCandidat(String emailCandidat);

    void deleteByIdOffre(Long idOffre);
}
