package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "catalogo")
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @OneToMany(mappedBy = "catalogo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pubblicazione> pubblicazioni = new HashSet<>();


    public Catalogo() {}


    public void addPubblicazione(Pubblicazione pubblicazione) {
        pubblicazioni.add(pubblicazione);
        pubblicazione.setCatalogo(this);
    }


    public void removePubblicazione(Pubblicazione pubblicazione) {
        pubblicazioni.remove(pubblicazione);
        pubblicazione.setCatalogo(null);
    }
}
