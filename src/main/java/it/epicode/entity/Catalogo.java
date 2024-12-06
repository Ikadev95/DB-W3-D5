package it.epicode.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Entity
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @OneToMany (mappedBy = "catalogo")
    Set<Pubblicazione> pubblicazioni = new HashSet<>();






}