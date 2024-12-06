package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NamedQuery(name = "get_by_card", query = "SELECT u FROM Utente u WHERE u.numero_di_tessera = :numero_di_tessera ")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column (name = "nome", nullable = false,length = 50)
    private String nome;

    @Getter
    @Setter
    @Column (name = "cognome", nullable = false,length = 50)
    private String cognome;

    @Getter
    @Setter
    @Column (name = "data_di_nascita")
    private LocalDate dataDiNascita;

    @Getter
    @Setter
    @Column (name = "numero_di_tessera", nullable = false)
    private int numero_di_tessera;

    @Getter
    @Setter
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Prestito> prestiti;

}
