package com.iwa.matching.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatProfile {
    private String email;
    private String firstName;
    private String lastName;
    private int gender;
    private Date birthDate;
    private String citizenship;
    private Adresse address;
    private String phone;
    private String photo; // URL de la photo
    private String cv; // URL du CV
    private String shortBio;
    private Etat etat = Etat.DISPONIBLE;
    private List<Reference> references;
    private List<Experience> experiences;
    private List<Disponibilite> disponibilites;
    private List<Opinion> opinions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Adresse {
        private String streetNum;
        private String street;
        private String complement;
        private String zipCode;
        private String city;
        private String country;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Experience {
        private String job;
        private String jobCategory;
        private Date startedAt;
        private Date endedAt;
        private Etablissement establishment;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Disponibilite {
        private String jobCategory;
        private Date startsAt;
        private Date endsAt;
        private List<String> places;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Opinion {
        private double score;
        private String message;
        private Long employerId;
        private Date providedAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reference {
        private String refName;
        private String refEstablishment;
        private Adresse refAddress;
        private String refPhone;
        private String refEmail;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Etablissement {
        private String establishmentName;
        private Adresse establishmentAddress;
    }

    public enum Etat {
        DISPONIBLE,
        INDISPONIBLE
    }
}
