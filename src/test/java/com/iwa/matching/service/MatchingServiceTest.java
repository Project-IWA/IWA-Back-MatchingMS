package com.iwa.matching.service;

import com.iwa.matching.model.CandidatProfile;
import com.iwa.matching.model.Offre;
import com.iwa.matching.util.MockDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MatchingServiceTest {

    @InjectMocks
    private MatchingService matchingService;

    private CandidatProfile candidat;
    private Offre offre;

    @BeforeEach
    void setUp() {
        // Initialize with mock data before each test
        candidat = MockDataUtil.createMockCandidatProfile();
        offre = MockDataUtil.createMockOffre();
    }

    @Test
    public void whenJobCategoryMatchesAndDatesAndLocationMatch_thenShouldMatch() {
        // Arrange: Set up offre and candidat to have matching criteria
        offre.setEmploi(candidat.getExperiences().get(0).getJobCategory());
        offre.setDateDebut(candidat.getDisponibilites().get(0).getStartsAt());
        offre.setDateFin(candidat.getDisponibilites().get(0).getEndsAt());
        offre.setVille(candidat.getDisponibilites().get(0).getPlaces().get(0));

        // Act: Call the method you want to test
        boolean result = matchingService.matchCandidatWithOffre(candidat, offre);

        // Assert: Check the result of the method call
        assertTrue(result, "The candidat should match with the offre");
    }

    @Test
    public void whenJobCategoryDoesNotMatch_thenShouldNotMatch() {
        // Arrange: Set up offre and candidat to have non-matching job categories
        offre.setEmploi("Non-matching Job Category");

        // Act & Assert
        assertFalse(matchingService.matchCandidatWithOffre(candidat, offre),
                "The candidat should not match with the offre when job categories don't match");
    }

    @Test
    public void whenDatesAreOutsideAvailability_thenShouldNotMatch() {
        // Arrange: Set up offre dates outside of candidat's availability
        // Assuming the candidat's availability starts and ends on specific dates, we set the offre's dates outside this range
        Offre mockOffre = MockDataUtil.createMockOffre();
        mockOffre.setDateDebut(new Date(2023, 1, 1)); // Set to a date before the candidat's availability
        mockOffre.setDateFin(new Date(2023, 1, 2)); // Set to a date just after the mockOffre's start date

        CandidatProfile mockCandidat = MockDataUtil.createMockCandidatProfile();
        // Ensure that the mock candidat's availability is after the mock offre's date range
        mockCandidat.getDisponibilites().get(0).setStartsAt(new Date(2023, 1, 3));
        mockCandidat.getDisponibilites().get(0).setEndsAt(new Date(2023, 1, 4));

        // Act: Call the method to test
        boolean result = matchingService.matchCandidatWithOffre(mockCandidat, mockOffre);

        // Assert: Check the result of the method call
        assertFalse(result, "The candidat should not match with the offre when dates are outside availability");
    }


    @Test
    public void whenLocationDoesNotMatch_thenShouldNotMatch() {
        // Arrange: Set up offre location that is not in candidat's place of availability
        offre.setVille("Non-matching Location");

        // Act & Assert
        assertFalse(matchingService.matchCandidatWithOffre(candidat, offre),
                "The candidat should not match with the offre when locations don't match");
    }

    @Test
    public void whenEtatIsNotDisponible_thenShouldNotMatch() {
        // Arrange: Set up offre location that is not in candidat's place of availability
        candidat.setEtat(CandidatProfile.Etat.INDISPONIBLE);

        // Act & Assert
        assertFalse(matchingService.matchCandidatWithOffre(candidat, offre),
                "The candidat should not match with the offre when etat is not disponible");
    }

}
