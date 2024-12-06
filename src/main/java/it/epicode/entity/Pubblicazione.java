package it.epicode.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "pubblicazioni")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pubblicazione", discriminatorType = DiscriminatorType.STRING)

@NamedQuery(name = "get_by_isbn", query = "SELECT p FROM Pubblicazione p WHERE p.ISBN = :isbn")
@NamedQuery(name = "get_by_yearp", query = "SELECT p FROM Pubblicazione p WHERE p.anno_di_pubblicazione = :anno_di_pubblicazione")
@NamedQuery(name = "get_by_title", query = "SELECT p FROM Pubblicazione p WHERE p.titolo LIKE :title")
public class Pubblicazione {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column (name = "isbn", nullable = false)
    private int ISBN;

    @Getter
    @Setter
    @Column (name = "titolo", nullable = false, length = 50)
    private String titolo;

    @Getter
    @Setter
    @Column (name = "anno_di_pubblicazione")
    private LocalDate anno_di_pubblicazione;

    @Getter
    @Setter
    @Column (name = "numero_pagine")
    private int numeroPagine;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "catalogo_id", nullable = false)
    private Catalogo catalogo;

    @Getter
    @Setter
    @OneToMany (mappedBy = "pubblicazione")
    private List<Prestito> listaPrestiti;



    public void stampa (){}

}
