package com.iwa.matching.util;

import java.util.Date;
import java.util.List;

import com.iwa.matching.model.CandidatProfile;
import com.iwa.matching.model.Offre;

public class MockDataUtil {

    public static Offre createMockOffre() {
        return new Offre(
                1L, // idOffre
                "Software Developer", // emploi
                new Date(), // dateDebut
                new Date(), // dateFin
                55000.0, // salaire
                "Health insurance, Stock options", // avantages
                "Open", // etat
                5, // nombreCandidats
                10L, // idUser
                20L, // idEtablissement
                "Paris" // ville
        );
    }

    public static CandidatProfile createMockCandidatProfile() {
        // Mock data for Adresse
        CandidatProfile.Adresse mockAddress = new CandidatProfile.Adresse(
                "123", // streetNum
                "Main Street", // street
                "Apt 4", // complement
                "75000", // zipCode
                "Paris", // city
                "France" // country
        );

        // Mock data for Etablissement
        CandidatProfile.Etablissement mockEtablissement = new CandidatProfile.Etablissement(
                "Software Solutions", // establishmentName
                mockAddress // establishmentAddress
        );

        // Mock data for Experience
        CandidatProfile.Experience mockExperience = new CandidatProfile.Experience(
                "Developer", // job
                "Software", // jobCategory
                new Date(), // startedAt
                new Date(), // endedAt
                mockEtablissement // establishment
        );

        // Mock data for Disponibilite
        CandidatProfile.Disponibilite mockDisponibilite = new CandidatProfile.Disponibilite(
                "Software", // jobCategory
                new Date(), // startsAt
                new Date(), // endsAt
                List.of("Paris", "Lyon") // places
        );

        // Mock data for Opinion
        CandidatProfile.Opinion mockOpinion = new CandidatProfile.Opinion(
                4.5, // score
                "Excellent work ethic", // message
                100L, // employerId
                new Date() // providedAt
        );

        // Mock data for Reference
        CandidatProfile.Reference mockReference = new CandidatProfile.Reference(
                "John Doe", // refName
                "Acme Corp", // refEstablishment
                mockAddress, // refAddress
                "0123456789", // refPhone
                "ref@example.com" // refEmail
        );

        // Assembling the mock CandidatProfile
        CandidatProfile mockCandidatProfile = new CandidatProfile(
                "candidate@email.com", // email
                "John", // firstName
                "Doe", // lastName
                1, // gender
                new Date(), // birthDate
                "French", // citizenship
                mockAddress, // address
                "0123456789", // phone
                "http://example.com/photo.jpg", // photo URL
                "http://example.com/cv.pdf", // cv URL
                "A brief bio about the candidate.", // shortBio
                CandidatProfile.Etat.DISPONIBLE, // etat
                List.of(mockReference), // references
                List.of(mockExperience), // experiences
                List.of(mockDisponibilite), // disponibilites
                List.of(mockOpinion) // opinions
        );

        return mockCandidatProfile;
    }
}
