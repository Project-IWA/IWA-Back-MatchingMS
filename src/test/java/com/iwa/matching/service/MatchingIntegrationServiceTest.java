package com.iwa.matching.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iwa.matching.model.Offre;
import com.iwa.matching.repository.MatcherCandidatRepository;
import com.iwa.matching.model.CandidatProfile;
import com.iwa.matching.model.MatcherCandidat;
import com.iwa.matching.util.MockDataUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MatchingIntegrationServiceTest {

    @Mock
    private MatcherCandidatRepository matcherCandidatRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MatchingService matchingService;

    @InjectMocks
    private MatchingIntegrationService matchingIntegrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before tests
    }

    @Test
    public void testPerformMatchingAndSaveResults() {
        // Setup mock data for Offre
        Offre mockOffre = MockDataUtil.createMockOffre();
        CandidatProfile mockCandidatProfile = MockDataUtil.createMockCandidatProfile();

        // When the restTemplate calls the Offres API, return a list containing the mock Offre
        when(restTemplate.getForEntity(anyString(), eq(Offre[].class)))
                .thenReturn(new ResponseEntity<>(new Offre[]{mockOffre}, HttpStatus.OK));

        // When the restTemplate calls the Candidat API, return an array containing the mock CandidatProfile
        when(restTemplate.getForObject(anyString(), eq(CandidatProfile[].class)))
                .thenReturn(new CandidatProfile[]{mockCandidatProfile});

        // Mock the behavior of the MatchingService
        when(matchingService.matchCandidatWithOffre(any(CandidatProfile.class), any(Offre.class)))
                .thenReturn(true); // Assuming true for a successful match

        // Call the method to test
        matchingIntegrationService.performMatchingAndSaveResults();

        // Verify that the repository's save method is called once
        verify(matcherCandidatRepository, times(1)).save(any(MatcherCandidat.class));

        // TODO: Add additional assertions to verify the correct MatcherCandidat is being saved
        // This might involve capturing the argument passed to the save method and asserting its properties
    }
}
