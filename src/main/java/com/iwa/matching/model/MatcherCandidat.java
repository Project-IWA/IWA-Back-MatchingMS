package com.iwa.matching.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matcher_candidat")
public class MatcherCandidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_offre")
    private Long idOffre;

    @Column(name = "email_candidat")
    private String emailCandidat;

    // Constructeurs, getters et setters générés par Lombok
}
