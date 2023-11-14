package com.iwa.matching.repository;

import com.iwa.matching.model.MatcherCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatcherCandidatRepository extends JpaRepository<MatcherCandidat, Long> {
    // Pour le moment, nous n'avons pas besoin de méthodes personnalisées
}
