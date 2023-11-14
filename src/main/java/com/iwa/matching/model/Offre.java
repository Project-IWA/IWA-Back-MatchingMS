package com.iwa.matching.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offre {
    private Long idOffre;
    private String emploi; // Correspond à la catégorie d'emploi pour le matching
    private Date dateDebut;
    private Date dateFin;
    private Double salaire;
    private String avantages;
    private String etat;
    private Integer nombreCandidats;
    private Long idUser; // ID de l'utilisateur (recruteur) qui a créé l'offre
    private Long idEtablissement; // ID de l'établissement lié à l'offre
    private String ville; // Localisation de l'offre, ajouté pour le matching des lieux
}
