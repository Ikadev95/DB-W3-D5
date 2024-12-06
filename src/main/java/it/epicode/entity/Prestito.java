package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
public class Prestito {

    public Prestito(Utente utente, Pubblicazione pubblicazione, LocalDate dataInizioPrestito) {
        this.utente = utente;
        this.pubblicazione = pubblicazione;
        this.dataInizioPrestito = dataInizioPrestito;
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }

    public Prestito() {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "pubblicazione_id", nullable = false)
    private Pubblicazione pubblicazione;

    @Getter
    @Setter
    @Column(name = "data_inizio_prestito", nullable = false)
    private LocalDate dataInizioPrestito;

    @Getter
    @Setter
    @Column(name = "data_restituzione_prevista", nullable = false)
    private LocalDate dataRestituzionePrevista;

    @Getter
    @Setter
    @Column(name = "data_restituzione_effettiva")
    private LocalDate dataRestituzioneEffettiva;

}
