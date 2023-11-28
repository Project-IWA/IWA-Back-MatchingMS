package com.iwa.matching.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data               // Generates getters, setters, toString, equals, and hashCode methods
@Builder            // Provides a builder pattern for object creation
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class Offre {
    private Long idOffre;
    private String emploi; // Correspond à la catégorie d'emploi pour le matching
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private Double salaire;
    private String avantages;
    private String etat;
    private Integer nombreCandidats;
    private String ville; // Localisation de l'offre, ajouté pour le matching des lieux
    private Long idUser; // ID de l'utilisateur (recruteur) qui a créé l'offre
    private Long idEtablissement; // ID de l'établissement lié à l'offre
    private TypeEmploi typeEmploi;

}
